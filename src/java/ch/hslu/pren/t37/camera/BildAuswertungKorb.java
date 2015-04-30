/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.pren.t37.camera;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import ch.hslu.pren.t37.Logger.PrenLogger;

/**
 *
 * @author Severin
 */
public class BildAuswertungKorb {
    
    private PrenLogger logger;

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public int bildAuswerten() {
      
        //Bild in dem gesucht werden soll
        String inFile = "../camera.jpg"; 
        //das Bild dass im infile gesucht wird
        String templateFile = "../Template_Korb/korb.jpg";
        //Lösung wird in diesem Bild präsentiert
        String outFile = "../LoesungsBild.jpg";
        //Überprüfungswert wird gesetzt
        int match_method = Imgproc.TM_CCOEFF_NORMED;

        //das original Bild und das zu suchende werden geladen
        Mat img = Highgui.imread(inFile, Highgui.CV_LOAD_IMAGE_COLOR);
        Mat templ = Highgui.imread(templateFile, Highgui.CV_LOAD_IMAGE_COLOR);

        // Lösungsmatrix generieren
        int result_cols = img.cols() - templ.cols() + 1;
        int result_rows = img.rows() - templ.rows() + 1;
        Mat result = new Mat(result_rows, result_cols, CvType.CV_32FC1);
        
        // Suchen und normalisieren
        Imgproc.matchTemplate(img, templ, result, match_method);
        Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1, new Mat());

        // Mit MinMax Logik wird der beste "Match" gesucht
        Core.MinMaxLocResult mmr = Core.minMaxLoc(result);

        Point matchLoc;
        if (match_method == Imgproc.TM_SQDIFF || match_method == Imgproc.TM_SQDIFF_NORMED) {
            matchLoc = mmr.minLoc;
        } else {
            matchLoc = mmr.maxLoc;
        }

        // Darstellen
        Core.rectangle(img, matchLoc, new Point(matchLoc.x + templ.cols(),
                matchLoc.y + templ.rows()), new Scalar(0, 255, 0), 10);

        // Alle 4 Eckpunkte speichern
        Point topLeft = new Point(matchLoc.x, matchLoc.y);
        Point topRight = new Point(matchLoc.x+templ.cols(), matchLoc.y);
        Point downLeft = new Point(matchLoc.x, matchLoc.y+templ.rows());
        Point downRight = new Point(matchLoc.x+templ.cols(), matchLoc.y+templ.rows());

        // Lösungsbild speichern
        Highgui.imwrite(outFile, img);

        //Mittelpunkt berechnen
        double mittePicture;
        double mitteKorb;
        double differnez;
        
        Mat sol = Highgui.imread(outFile, Highgui.CV_LOAD_IMAGE_COLOR);
         
        mittePicture = sol.width() / 2;
        mitteKorb = (topRight.x - topLeft.x) / 2;
        mitteKorb = topLeft.x + mitteKorb;
        differnez = mitteKorb - mittePicture;
        
        logger.log(PrenLogger.LogLevel.DEBUG, "Mitte Korb: " + mitteKorb);
        logger.log(PrenLogger.LogLevel.DEBUG, "Mitte Bild: " + mittePicture);
        logger.log(PrenLogger.LogLevel.DEBUG, "Differenz: " + differnez + "\nWenn Differnez negativ, nach rechts drehen");
        
        return (int)differnez;
    }    
}
