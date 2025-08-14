package com.example;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("==== AI-Based Recommendation System (Mahout) ====");

        Scanner scanner = new Scanner(System.in);

        // Get user ID
        System.out.print("Enter user ID (e.g., 1..5): ");
        long userId = scanner.nextLong();

        // Get number of recommendations
        System.out.print("How many recommendations? ");
        int numRecommendations = scanner.nextInt();

        // Choose algorithm
        System.out.print("Algorithm [1=User-Based, 2=Item-Based] (default 1): ");
        String algoChoice = scanner.nextLine().trim(); // read newline from previous input
        if (algoChoice.isEmpty()) algoChoice = "1";

        // Load dataset
        DataModel model = new FileDataModel(new File("data.csv"));

        if (algoChoice.equals("2")) {
            // Item-Based Recommendation
            ItemSimilarity similarity = new LogLikelihoodSimilarity(model);
            GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(model, similarity);

            List<RecommendedItem> recommendations = recommender.recommend(userId, numRecommendations);

            System.out.println("\nTop-" + numRecommendations + " recommendations (Item-Based):");
            for (RecommendedItem rec : recommendations) {
                System.out.printf("Item %d -> score %.4f%n", rec.getItemID(), rec.getValue());
            }

        } else {
            // User-Based Recommendation
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(3, similarity, model);
            GenericUserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

            List<RecommendedItem> recommendations = recommender.recommend(userId, numRecommendations);

            System.out.println("\nTop-" + numRecommendations + " recommendations (User-Based):");
            for (RecommendedItem rec : recommendations) {
                System.out.printf("Item %d -> score %.4f%n", rec.getItemID(), rec.getValue());
            }
        }

        scanner.close();
    }
}
