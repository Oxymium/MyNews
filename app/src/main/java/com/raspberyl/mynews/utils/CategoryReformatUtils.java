package com.raspberyl.mynews.utils;



public class CategoryReformatUtils {


    // Display String as「Section」 > 「Subsection」, and only 「Section」 if subsection is empty
    // If [Section} equals 「Subsection」, returns only 「Subsection」 to avoid repetition

    public static String reformatCategory(String category, String sub_category){

        String output_category = category;
        String output_category_sub_category = category + " > " + sub_category;

        if (sub_category == null) {
            return output_category;
        }
        if (sub_category.isEmpty() || category.equals(sub_category)) {
            return output_category;
        } else {
            return output_category_sub_category;
        }
    }

}
