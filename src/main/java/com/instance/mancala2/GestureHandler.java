package com.instance.mancala2;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class GestureHandler {

    private Point2D lastEndPoint;
    private final List<String> directionsLog = new ArrayList<>();
    private double tolerance = 20; // Tolerance for detecting direction change

    // Constructor with tolerance parameter
    public GestureHandler(double tolerance) {
        this.tolerance = tolerance;
    }

    public void onDragStarted(double x, double y) {
        Point2D startPoint = new Point2D(x, y);
        lastEndPoint = startPoint;
        directionsLog.clear(); // Clear previous directions
        System.out.println("Drag started at: " + startPoint);
    }

    public void onDrag(double x, double y, Group parentGroup) {
        Point2D currentPoint = new Point2D(x, y);

        // Only log direction if the movement is beyond the tolerance
        if (lastEndPoint.distance(currentPoint) > tolerance) {
            String direction = calculateDirection(lastEndPoint, currentPoint);
            directionsLog.add(direction);
            System.out.println("Direction: " + direction);

            // Visual feedback for direction change
            markDirectionChange(currentPoint, parentGroup);

            lastEndPoint = currentPoint; // Update lastEndPoint for the next direction calculation
        }
    }

    public void onDragEnded() {
        System.out.println("Drag ended");
        logDirections();
    }

    private String calculateDirection(Point2D from, Point2D to) {
        double dx = to.getX() - from.getX();
        double dy = to.getY() - from.getY();

        if (Math.abs(dx) > Math.abs(dy)) {
            return dx > 0 ? "RIGHT" : "LEFT";
        } else {
            return dy > 0 ? "DOWN" : "UP";
        }
    }

    private void markDirectionChange(Point2D point, Group parentGroup) {
        Circle mark = new Circle(point.getX(), point.getY(), 3, Color.BLUE);
        parentGroup.getChildren().add(mark);
    }

    private void logDirections() {
        System.out.println("Directions Log:");
        for (String direction : directionsLog) {
            System.out.println(direction);
        }
    }
}
