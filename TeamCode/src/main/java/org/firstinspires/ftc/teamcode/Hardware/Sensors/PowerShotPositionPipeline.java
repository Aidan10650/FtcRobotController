package org.firstinspires.ftc.teamcode.Hardware.Sensors;

import android.util.Pair;
import org.firstinspires.ftc.teamcode.Utilities.Vector2D;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class PowerShotPositionPipeline extends OpenCvPipeline {

    static class PairComparator implements Comparator<Pair<Double, MatOfPoint>>{

        @Override
        public int compare(Pair<Double, MatOfPoint> doubleMatOfPointPair, Pair<Double, MatOfPoint> t1) {
            return doubleMatOfPointPair.first.compareTo(t1.first);
        }
    }

    static class HeightComparator implements  Comparator<Rect>{

        @Override
        public int compare(Rect rect, Rect t1) {
            if(t1.empty() || rect.empty()) return 0;
            if(0.25*rect.width>Math.abs(rect.width-t1.width) || 0.25*t1.width>Math.abs(rect.width-t1.width)) {
                return 0;
            } else if (rect.width>t1.width){
                return 1;//was 1
            } else {
                return -1;///was -1
            }
        }
    }

    static class LeftRightComparator implements  Comparator<Rect>{

        @Override
        public int compare(Rect rect, Rect t1) {
            if(t1.empty() || rect.empty()) return 0;

            if(rect.y>t1.y) return 1;

             else return -1;

        }
    }
        Mat maskPowerShot = new Mat();
        Mat maskGoal = new Mat();
        Point p = new Point(-1, -1);
        Mat nInput = new Mat();

        ArrayList<Rect> rectArray = new ArrayList<Rect>();

        Vector2D retV = new Vector2D(-1, -1);

        /*
         * Points which actually define the sample region rectangles, derived from above values
         *
         * Example of how points A and B work to define a rectangle
         *
         *   ------------------------------------
         *   | (0,0) Point A                    |
         *   |                                  |
         *   |                                  |
         *   |                                  |
         *   |                                  |
         *   |                                  |
         *   |                                  |
         *   |                  Point B (70,50) |
         *   ------------------------------------
         *
         */

        float centerXFirst = -1;
        float centerYFirst = -1;

        Mat YCrCb = new Mat();
        Mat Cb = new Mat();

        private volatile int height = -1;
        private volatile double heightWidthRatio = 0;


        Rect boundingRect = new Rect(-1,-1,-1,-1);
        Rect boundingRect2 = new Rect(-1,-1,-1,-1);
        Rect boundingRect3 = new Rect(-1,-1,-1,-1);

        int fileCounter = 0;


        void inputToCb(Mat input)
        {
            Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);
            Core.extractChannel(YCrCb, Cb, 2);
        }

        @Override
        public void init(Mat firstFrame)
        {

            inputToCb(firstFrame);

        }

        public static boolean processGoalActive = false;
        public static boolean processPowershotActive = false;

        @Override
        public Mat processFrame(Mat input) {
            if(processGoalActive){
                return processGoal(input);
            }
            if(processPowershotActive){

                return processPowerShot(input);
            }
            return input;
        }
        private Mat processGoal(Mat input){
            final Scalar lower = new Scalar(0, 25, 50);
            final Scalar upper = new Scalar(12, 255, 255);
//            Mat nHSV = new Mat();

//            Rect rectCrop = new Rect(0, 0, input.width()/2, input.height());
//            Mat hsv = new Mat(nHSV, rectCrop);
            Mat hsv = new Mat();
            Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);

            Core.inRange(hsv, lower, upper, maskGoal);

//            mask = mask.submat(0, mask.rows()/3, 0, mask.cols());

            Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size((24) + 1, (24)+1));
            Imgproc.erode(maskGoal, maskGoal, kernel);
            Imgproc.dilate(maskGoal, maskGoal, kernel);



            List<MatOfPoint> contours = new ArrayList<>();
            Mat hierarchy = new Mat();
            Imgproc.findContours(maskGoal, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
            double maxArea = 0;
            double secondMaxArea = 0;

            MatOfPoint firstContour = new MatOfPoint();
            MatOfPoint secondContour = new MatOfPoint();
            Collections.sort(contours, new GoalPositionPipeline.AreaComparator());
            List<MatOfPoint> contours2 = new ArrayList<>();
            for(MatOfPoint c:contours) {
                Rect r = Imgproc.boundingRect(c);
                if(r.x > (1280/3)) continue;
                if(r.width < (1280/10)) continue;
                contours2.add(c);
            }
//            for (MatOfPoint contour : contours) {
//                double area = Imgproc.contourArea(contour);
//                if (area > maxArea) {
//                    maxArea = area;
//                    firstContour = contour;
//                } else if (area > secondMaxArea) {
//                    secondMaxArea = area;
//                    secondContour = contour;
//                }
//            }

            if(contours2.size()>0) {
                boundingRect = Imgproc.boundingRect(contours2.get(0));
                p = new Point((boundingRect.tl().x + boundingRect.br().x) / 2.0,
                        (boundingRect.tl().y + boundingRect.br().y) / 2.0);
            }
            if(contours2.size()>1) {
                boundingRect2 = Imgproc.boundingRect(contours2.get(1));
                Point p2 = new Point((boundingRect2.tl().x + boundingRect2.br().x) / 2.0,
                        (boundingRect2.tl().y + boundingRect2.br().y) / 2.0);
                p.x = (p.x + p2.x)/2.0;
                p.y = (p.y + p2.y)/2.0;
            }


//            Moments mFirst = Imgproc.moments(mask, true);
//
//            centerXFirst = (float)(mFirst.m10 / mFirst.m00);
//            centerYFirst = (float)(mFirst.m01 / mFirst.m00);
//            if(Float.isNaN(centerXFirst)||Float.isNaN(centerYFirst)){
//                centerXFirst = -1;
//                centerYFirst = -1;
//            }

//            hsvArr = hsv.get(120, 160);
//            Imgproc.putText(input, Double.toString(hsvArr[0]) + ' ' + Double.toString(hsvArr[1]) + ' ' + Double.toString(hsvArr[2]), new Point(100, 100), 1, 1, new Scalar( 0, 255, 0));
//            Imgproc.circle(input, new Point(160, 120), 5, new Scalar(255, 0, 0));
//
//            Imgproc.circle(mask, new Point(centerXFirst, centerYFirst), 5, new Scalar(255, 0 ,0));

//            int lowestX = boundingRect.x<boundingRect2.x?boundingRect.x:boundingRect2.x;
//            int lowestY = boundingRect.y<boundingRect2.y?boundingRect.y:boundingRect2.y;
//            int highestX = boundingRect.x+boundingRect.width>boundingRect2.x+boundingRect2.width?boundingRect.x+boundingRect.width:boundingRect2.x+boundingRect2.width;
//            int highestY = boundingRect.y+boundingRect.height>boundingRect2.y+boundingRect2.height?boundingRect.y+boundingRect.height:boundingRect2.y+boundingRect2.height;
//            //new MatOfRect(boundingRect, boundingRect2)
//
//            finalBoundingRect = new Rect (lowestX, lowestY, highestX-lowestX, highestY-lowestY);


            Imgproc.rectangle(maskGoal, boundingRect, new Scalar(100, 255, 255), 10);
//            Imgproc.putText(mask, Integer.toString(lowestX) + " " + Integer.toString(lowestY) + " " + Integer.toString(highestX) + " " + Integer.toString(highestY), new Point(100, 100), 1, 1, new Scalar( 255, 255, 255));
            Imgproc.rectangle(maskGoal, boundingRect2, new Scalar(100, 255, 255), 10);
//            Imgproc.rectangle(mask, finalBoundingRect, new Scalar(50, 50, 50));

            Imgproc.cvtColor(maskGoal, maskGoal, Imgproc.COLOR_GRAY2RGB);


            hsv.release();
            hierarchy.release();
            kernel.release();
//            nHSV.release();

            return maskGoal;
        }

        private Mat processPowerShot(Mat input){
            try {
                final Scalar lower = new Scalar(0, 110, 50);
                final Scalar upper = new Scalar(10, 255, 240);

                Mat nHSV = new Mat();
                nInput = input.clone();

                Imgproc.cvtColor(input, nHSV, Imgproc.COLOR_RGB2HSV);

                Rect rectCrop = new Rect(0, 0, input.width()/2, input.height());
                Mat hsv = new Mat(nHSV, rectCrop);

                Core.inRange(hsv, lower, upper, maskPowerShot);

                Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size((2) + 1, (2) + 1));
                Imgproc.erode(maskPowerShot, maskPowerShot, kernel);
                Imgproc.dilate(maskPowerShot, maskPowerShot, kernel);


                List<MatOfPoint> contours = new ArrayList<>();
                Mat hierarchy = new Mat();
                Imgproc.findContours(maskPowerShot, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
                ArrayList<Pair<Double, MatOfPoint>> doubleArrayList = new ArrayList<Pair<Double, MatOfPoint>>();
                for (MatOfPoint contour : contours) {
                    if (Imgproc.boundingRect(contour).height > 100 ||
                        Imgproc.boundingRect(contour).width > 150 ||
                        Imgproc.boundingRect(contour).width < 90 ||
                        Imgproc.boundingRect(contour).height < 5) continue;

                    doubleArrayList.add(new Pair<Double, MatOfPoint>((double) Imgproc.boundingRect(contour).width, contour));
                }

                //Sort by real-world-height
                if (!doubleArrayList.isEmpty()) {
                    Collections.sort(doubleArrayList, new PairComparator());
                    Collections.reverse(doubleArrayList);
                }

                // Create bounding rects
                int i = 0;
                rectArray.clear();
                for (Pair<Double, MatOfPoint> p : doubleArrayList) {
                    Rect b = Imgproc.boundingRect(p.second);
                    if (b.empty()) continue;

                    rectArray.add(b);
                    Imgproc.rectangle(maskPowerShot, Imgproc.boundingRect(p.second), new Scalar(255, 0, 0), 4);
                    Imgproc.rectangle(nInput, Imgproc.boundingRect(p.second), new Scalar(0, 255, 0), 10);

                    i++;
                    //Limiting to 3 rects
//                  if (i > 0) break;
                }


//            if(doubleArrayList.size()>2) {
//                Imgproc.putText(maskPowerShot, Double.toString(Imgproc.boundingRect(doubleArrayList.get(0).second).area()) + " " + Double.toString(Imgproc.boundingRect(doubleArrayList.get(0).second).width), new Point(100, 100), 1, 3, new Scalar(255, 0, 0));
//            }




                List<Rect> rArray;
//                if(!rectArray.isEmpty()) {
//                    try {
                        Collections.sort(rectArray, new LeftRightComparator());
                        Collections.reverse(rectArray);
//                        if(rectArray.size() > 2) {
//                            rArray = rectArray.subList(0, 2);
//                            Collections.sort(rArray, new HeightComparator());
//                            for (Rect r : rArray) {
//                                Imgproc.rectangle(maskPowerShot, r, new Scalar(0, 255, 255), 4);
//                            }
//                        }


//
//                    } catch (Exception e) {
//                        rArray = null;
//                    }
//
//
//                }

                hsv.release();
                hierarchy.release();
                nHSV.release();
                kernel.release();
//                if(fileCounter<300) saveMatToDisk(nInput, "powershot" + fileCounter++);
                return maskPowerShot;
            } catch (Exception e){
               return null;
            }
        }

        /*
         * Call this from the OpMode thread to obtain the latest analysis
         */
        public Vector2D getPowerCenter() {
            if (!rectArray.isEmpty()) {
                try {
                    //sort left right
                    Collections.sort(rectArray, new LeftRightComparator());
                    Collections.reverse(rectArray);
                    return new Vector2D(rectArray.get(0).width/*rectArray.get(0).x + rectArray.get(0).width / 2.0*/, rectArray.get(0).y + rectArray.get(0).height / 2.0);
                    //                    List<Rect> rArray = rectArray.subList(0, 2);
//                    Collections.sort(rArray, new HeightComparator());
//                    return new Vector2D(rArray.get(0).x + rArray.get(0).width / 2.0, rArray.get(0).y + rArray.get(0).height / 2.0);

                }catch(Exception e)
                {
                    return new Vector2D(-1, -1);
                }
            } else {
                return new Vector2D(-1, -1);
            }
        }

        public Vector2D getGoalPos(){ return new Vector2D(p.x, p.y); }
}
