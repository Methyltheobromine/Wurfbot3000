package ch.hslu.pren.t37.logic;

import ch.hslu.pren.t37.Logger.PrenLogger;
import ch.hslu.pren.t37.camera.PictureEvaluator;
import ch.hslu.pren.t37.camera.CameraPictureHandler;
import ch.hslu.pren.t37.pythoninterop.ASignalHandler;
import ch.hslu.pren.t37.pythoninterop.ISignalHandler;
import java.io.IOException;
import static java.lang.Math.abs;
import java.util.ArrayList;

/**
 * Executes the main controller logic.
 * 
 * @author Team 37
 */
public final class Logic {

    private ISignalHandler _sigHandler;
    private ASignalHandler _aSignalH;
    private DCEngineHandler _dcHandlerE;
    private CameraPictureHandler _bvw;
    private PictureEvaluator _bak;
    private StepperTurret _st;
    private StepperMagazine _sM;
    private PrenLogger logger;

    //Autonomer Ablauf
    private static double PIXEL_TO_STEP_CONVERSION; // Dividend bei Pixel zu Drehturmschritten
    private static String DC_STOP_SIGNAL; // DC Motor Stop Signal
    private static int BALL_COUNTER; // Anzahl Bälle
    private static String dcSPEED; //RPM Speed of DC-Engine 000 = Stop 199 = Max Speed
    private static String LOGLEVEL; //LogLevel
    private static int SLEEPTIME; //Zeit die zwischen den Release Balls geschlafen werden soll

    //Initialization
    private static int TURRET_DIST_MIDDLE; // Wert bei Initialisierung um in die Mitte zu fahren
    private static String STEPPS_RELEASE_BALLS; // Wert bei Initialisierung um due Ballzuführung zu optimieren


    private static final String LIGHT_BARRIER = "/home/pi/Wurfbot/PeripherieAnsteuerung/Light_Barrier.py";
    private static final String CAMERA = "/home/pi/Wurfbot/PeripherieAnsteuerung/Camera.py";
    private static final String TURRET = "/home/pi/Wurfbot/PeripherieAnsteuerung/Turret.py";
    private static final String UART = "/home/pi/Wurfbot/PeripherieAnsteuerung/UART.py";
    private static final String INITIALIZATION_WURFBOT = "/home/pi/Wurfbot/PeripherieAnsteuerung/Turret_Position_Initialization.py";

    /**
     * Gets the current DC-Speed.
     * @return DC-Speed represented as a String.
     */
    public String getDcSPEED() {
        return dcSPEED;
    }

    /**
     * Constructor.
     * 
     * @throws IOException if an IO-Read/Write exception occurs.
     * @throws InterruptedException if the loading is interrupted. 
     */
    public Logic() throws IOException, InterruptedException {
        loadVariableContent();
    }

    /**
     * Loads the content of the Property-File and sets them to their specific variables.
     * @return A HTML-Formated String containing all properties.
     */
    public String loadVariableContent() {
        //Autonomer Ablauf
        PIXEL_TO_STEP_CONVERSION = Double.parseDouble(PropertyFileHandler.getPropertyFile("PIXEL_TO_STEP_CONVERSION"));
        DC_STOP_SIGNAL = PropertyFileHandler.getPropertyFile("DC_STOP_SIGNAL"); 
        BALL_COUNTER =  Integer.parseInt(PropertyFileHandler.getPropertyFile("BALL_COUNTER"));
        dcSPEED = PropertyFileHandler.getPropertyFile("dcSPEED");
        LOGLEVEL = PropertyFileHandler.getPropertyFile("LogLevel");
        SLEEPTIME = Integer.parseInt(PropertyFileHandler.getPropertyFile("SLEEPTIME"));

        //Initialization
        TURRET_DIST_MIDDLE = Integer.parseInt(PropertyFileHandler.getPropertyFile("TURRET_DIST_MIDDLE"));
        STEPPS_RELEASE_BALLS = PropertyFileHandler.getPropertyFile("STEPPS_RELEASE_BALLS");
        
        PrenLogger.setCurrentLoglevel(PrenLogger.LogLevel.valueOf(LOGLEVEL));
        String values = ("<b>Folgende Werte wurden aus dem config.properties geladen: </b><br/><br/>"
                + "<b>Autonomer Ablauf: </b><br/>"
                + "PIXEL_TO_STEP_CONVERSION : " + PIXEL_TO_STEP_CONVERSION + "<br/>"
                + "DC_STOP_SIGNAL : " + DC_STOP_SIGNAL + "<br/>"
                + "BALL_COUNTER : " + BALL_COUNTER + "<br/>"
                + "dcSPEED : " + dcSPEED + "<br/>"
                + "LogLevel : " + LOGLEVEL + "<br/>"
                + "SleepTime : " + SLEEPTIME + "<br/><br/>"
                + "<b>Initialization: </b><br/>"
                + "TURRET_DIST_MIDDLE : " + TURRET_DIST_MIDDLE + "<br/>"
                + "STEPPS_RELEASE_BALLS : " + STEPPS_RELEASE_BALLS);
                
        return values;
    }

    /**
     * Moves the turret to its initial position and initialises the slice to release the Balls.
     * 
     * @throws IOException if an IO-Read/Write exception occurs.
     * @throws InterruptedException if the loading is interrupted. 
     */
    public void startInitalization() throws IOException, InterruptedException {
        TurretPositionInitialization turretPositionInitialization = new TurretPositionInitialization(INITIALIZATION_WURFBOT, new ArrayList<String>());
        // Move to the left until the End-Switch is reached
        turretPositionInitialization.runPythonScript();
        String signal = turretPositionInitialization.evaluateScriptOutput();
        if (!signal.equals("Ready")) {
            throw new IOException("Initilization failed");
        }
        turretPositionInitialization.stopPythonProcess();
        Thread.sleep(10);
        // Move to the Middle, on the basis of the property how was read
        positionTurret(TURRET_DIST_MIDDLE, "1");
        // Initialize the slice who is responsible for the Balls.
        releaseBalls(STEPPS_RELEASE_BALLS);
    }

    /**
     * Executes the main logic. 1. takes the picture 2. evaluates the template
     * matching 3. executes the turret stepper. 4. starts the engine 5. releases
     * the magazine
     *
     * @throws IOException if an IO-Read/Write exception occurs.
     * @throws InterruptedException if the loading is interrupted. 
     */
    public void wurfbot3000Start() throws InterruptedException, IOException {
        int camSteps = getCalculatedStepsFromCamera();
        if (camSteps != 0) {
            String direction = camSteps < 0 ? "0" : "1";
            positionTurret(abs(camSteps), direction);
        }
        startDCEngine();
        Thread.sleep(2000);
        for (int i = 1; i <= BALL_COUNTER; i++) {
            releaseBalls("0");
            Thread.sleep(SLEEPTIME);
        }
        dcEngineStop();
    }

    /**
     * Gets the steps from the picture evaluation.
     *
     * @return the converted steps for the turret positioning.
     */
    private int getCalculatedStepsFromCamera() throws IOException, InterruptedException {
        double steps = 0;
        //Foto aufnehmen
        CameraPictureHandler pictureFromWebcam = new CameraPictureHandler(CAMERA, new ArrayList<String>());
        pictureFromWebcam.runPythonScript();
        pictureFromWebcam.stopPythonProcess();
        //Foto auswerten
        PictureEvaluator bildauswertung = new PictureEvaluator();
        int stepsInPixel = bildauswertung.evaluatePicture();
        steps = stepsInPixel / PIXEL_TO_STEP_CONVERSION; // int / double --> double
        steps = (int) Math.round(steps);

        int stepsToTurn = (int) steps;
        return stepsToTurn;
    }

    /**
     * Positions the Turret.
     *
     * @param camSteps to be turned.
     * @param direction to be turned.
     */
    private void positionTurret(int camSteps, String direction) throws IOException, InterruptedException {
        ArrayList<String> argsP = new ArrayList<>();
        argsP.add(Integer.toString(camSteps));
        argsP.add(direction);
        StepperTurret stepperTurret = new StepperTurret(TURRET, argsP);
        stepperTurret.runPythonScript();
        stepperTurret.stopPythonProcess();
    }

    /**
     * Starts the DC Engine.
     *
     * @throws IOException if an IO-Read/Write exception occurs.
     * @throws InterruptedException if the loading is interrupted. 
     */
    private void startDCEngine() throws InterruptedException, IOException {
        ArrayList<String> argsP = new ArrayList<>();
        argsP.add(getDcSPEED());
        DCEngineHandler dcEngineHandler = new DCEngineHandler(UART, argsP);
        dcEngineHandler.runPythonScript();
        dcEngineHandler.stopPythonProcess();
    }

    /**
     * Stops the DC Engine.
     *
     * * @throws IOException if an IO-Read/Write exception occurs.
     * @throws InterruptedException if the loading is interrupted. 
     */
    private void dcEngineStop() throws IOException, InterruptedException {
        ArrayList<String> argsP = new ArrayList<>();
        argsP.add(DC_STOP_SIGNAL);
        DCEngineHandler dcEngineHandler = new DCEngineHandler(UART, argsP);
        dcEngineHandler.runPythonScript();
        dcEngineHandler.stopPythonProcess();
    }

    /**
     * Releases the Magazine.
     *
     * @throws IOException if an IO-Read/Write exception occurs.
     * @throws InterruptedException if the loading is interrupted. 
     */
    private void releaseBalls(String stepps) throws IOException, InterruptedException {
        ArrayList<String> argsP = new ArrayList<>();
        argsP.add(stepps);
        StepperMagazine stepperFeedingBalls = new StepperMagazine(LIGHT_BARRIER, argsP);
        stepperFeedingBalls.runPythonScript();
        String signal = stepperFeedingBalls.evaluateScriptOutput();
        if (!signal.equals("Ready")) {
            throw new IOException("Initilization failed");
        }
        stepperFeedingBalls.stopPythonProcess();
        Thread.sleep(SLEEPTIME);
    }
}
