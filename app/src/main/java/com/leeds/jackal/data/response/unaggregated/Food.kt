package com.leeds.jackal.data.response.unaggregated


import com.google.gson.annotations.SerializedName

data class Food(
    @SerializedName("alt_measures")
    val altMeasures: List<AltMeasure>,
    @SerializedName("brand_name")
    val brandName: Any?,
    @SerializedName("consumed_at")
    val consumedAt: String,
    @SerializedName("food_name")
    val foodName: String,
    @SerializedName("full_nutrients")
    val fullNutrients: List<FullNutrient>,
    val lat: Any?,
    val lng: Any?,
    @SerializedName("meal_type")
    val mealType: Int,
    val metadata: Metadata,
    @SerializedName("ndb_no")
    val ndbNo: Int,
    @SerializedName("nf_calories")
    val nfCalories: Double,
    @SerializedName("nf_cholesterol")
    val nfCholesterol: Double,
    @SerializedName("nf_dietary_fiber")
    val nfDietaryFiber: Double,
    @SerializedName("nf_p")
    val nfP: Double,
    @SerializedName("nf_potassium")
    val nfPotassium: Double,
    @SerializedName("nf_protein")
    val nfProtein: Double,
    @SerializedName("nf_saturated_fat")
    val nfSaturatedFat: Double,
    @SerializedName("nf_sodium")
    val nfSodium: Double,
    @SerializedName("nf_sugars")
    val nfSugars: Double,
    @SerializedName("nf_total_carbohydrate")
    val nfTotalCarbohydrate: Double,
    @SerializedName("nf_total_fat")
    val nfTotalFat: Double,
    @SerializedName("nix_brand_id")
    val nixBrandId: Any?,
    @SerializedName("nix_brand_name")
    val nixBrandName: Any?,
    @SerializedName("nix_item_id")
    val nixItemId: Any?,
    @SerializedName("nix_item_name")
    val nixItemName: Any?,
    val photo: Photo,
    @SerializedName("serving_qty")
    val servingQty: Int,
    @SerializedName("serving_unit")
    val servingUnit: String,
    @SerializedName("serving_weight_grams")
    val servingWeightGrams: Double,
    val source: Int,
    @SerializedName("sub_recipe")
    val subRecipe: Any?,
    val tags: Tags,
    val upc: Any?
)