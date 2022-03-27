package com.hossamelsharkawy.simplecart.data.source.local

import com.hossamelsharkawy.simplecart.app.features.cart.CartEvent
import com.hossamelsharkawy.simplecart.data.entities.Category
import com.hossamelsharkawy.simplecart.data.entities.Product
import com.hossamelsharkawy.simplecart.data.entities.Unit

/*________________________________________________________________________________*/

//https://www.instacart.com/image-server/257x257/filters:fill(FFF,true):format(jpg)/d2lnr5mha7bycj.cloudfront.net/product-image/file/large_8402b3ca-f2ec-4fa2-932d-63d09db05744.png

//"https://www.instacart.com/image-server/200x/filters:fill(FFF,true):format(jpg)/d2lnr5mha7bycj.cloudfront.net/product-image/file/"
private val BaseImageUrl =
    "https://www.instacart.com/image-server/200x200/filters:fill(FFF,true):format(jpg)/d2lnr5mha7bycj.cloudfront.net/product-image/file/"

const val BaseImageUrlPart1 = "https://www.instacart.com/image-server/"
const val BaseImageUrlPart2 =
    "/filters:fill(FFF,true):format(jpg)/d2lnr5mha7bycj.cloudfront.net/product-image/file/"

fun getBaseImageUrl(size: Int) =
    BaseImageUrlPart1.plus(size).plus("x").plus(size).plus(BaseImageUrlPart2)


val fakeCartEvent = object : CartEvent {
    override fun onRemoveAll() {
        TODO("Not yet implemented")
    }

    override fun onCheckOut() {
        TODO("Not yet implemented")
    }

    override fun onAddItemClick(product: Product) {
        TODO("Not yet implemented")
    }

    override fun onRemoveItemClick(product: Product) {
        TODO("Not yet implemented")
    }

    override fun onProductInfoClick(product: Product) {
        TODO("Not yet implemented")
    }

    override fun onSelectQtyClick(qty: Int, product: Product) {
        TODO("Not yet implemented")
    }
}

/*________________________________________________________________________________*/
val FruitsCategory = Category("Fruits", 1)
val VegetablesCategory = Category("Vegetables", index = 2)
val MeatCategory = Category("Meat", index = 3)
val ChickenCategory = Category("Chicken", index = 4)
val FishCategory = Category("Fish", index = 5)

/*________________________________________________________________________________*/

val kiloUnit = Unit.KUnit(1)
val gramsUnit = Unit.GramUnit(500)
val packUnit = Unit.PackUnit(1)


val fakeProduct =
    Product(
        2,
        "asdasssaaaaaaaaaaaaaaaa",
        qtyInCart = 5,
        price = "55",
        image = Images.AnjouPears,
        category = FruitsCategory
    )

val fakeProducts = arrayListOf(
    Product(
        1,
        "Long Title Long Title Long Title Long Title Long Title Long Title Long Title Long Title Long Title Long Title",
        price = "55",
        category = FruitsCategory
    ),
    Product(2, "Short Title 2", qtyInCart = 0, price = "66", category = FruitsCategory),
    Product(
        1,
        "Long Title Long Title Long Title Long Title Long Title 2",
        price = "70",
        qtyInCart = 2, category = FruitsCategory
    ),
    Product(4, "Short Title N", qtyInCart = 0, price = "100", category = FruitsCategory),
    Product(5, "Short Title T", qtyInCart = 2, price = "50", category = FruitsCategory),
    Product(5, "Short Title T", qtyInCart = 2, price = "50", category = FruitsCategory),
    Product(5, "Short Title T", qtyInCart = 0, price = "50", category = FruitsCategory),
    Product(5, "Short Title T", qtyInCart = 0, price = "50", category = FruitsCategory),
    Product(6, "Short Title B", qtyInCart = 0, price = "100", category = FruitsCategory)
)

val fakeProducts2 = arrayListOf<Product>().apply {

    add(
        Product(
            id = 1,
            title = "Organic Bananas",
            image = Images.OrganicBananas,
            price = "50.00",
            category = FruitsCategory,
        )
    )

    add(
        Product(
            id = 140,
            title = "Strawberries",
            image = Images.Strawberries,
            price = "50.00",
            category = FruitsCategory,
        )
    )

    add(
        Product(
            id = 141,
            title = "Anjou Pears",
            image = Images.AnjouPears,
            price = "50.00",
            category = FruitsCategory,
        )
    )

    add(
        Product(
            id = 142,
            title = "Candy Grapes",
            image = Images.CandyGrapes,
            price = "50.00",
            category = FruitsCategory,
        )
    )
    add(
        Product(
            id = 143,
            title = "Red Grapes",
            image = Images.RedGrapes,
            price = "50.00",
            category = FruitsCategory,
        )
    )
    add(
        Product(
            id = 5,
            title = "Avocado",
            image = Images.Avocado,
            price = "50.00",
            category = FruitsCategory,
        )
    )
    add(
        Product(
            id = 6,
            title = "Monte Pineapple",
            image = Images.MonteGoldPineapple,
            price = "50.00",
            category = FruitsCategory,
        )
    )
    add(
        Product(
            id = 2,
            title = "Orange",
            image = Images.Orange,
            price = "50.00",
            category = FruitsCategory,
        )
    )
    add(
        Product(
            id = 10,
            title = "Mandarin",
            image = Images.Nandarin,
            price = "50.00",
            category = FruitsCategory,
        )
    )

    add(
        Product(
            id = 3,
            title = "Golden Apple",
            image = Images.GoldenDeliciousApple,
            price = "50.00",
            category = FruitsCategory,
        )
    )

    add(
        Product(
            id = 4,
            title = "Red Apple",
            image = Images.RedDeliciousApple,
            price = "50.00",
            category = FruitsCategory,
        )
    )

    add(
        Product(
            id = 155,
            title = "Green Apple",
            image = Images.GreenApple,
            price = "50.00",
            category = FruitsCategory,
        )
    )

    add(
        Product(
            id = 7,
            title = "Watermelon",
            image = Images.Watermelon,
            price = "50.00",
            category = FruitsCategory,
        )
    )

    add(
        Product(
            id = 9,
            title = "Spaghetti Squash",
            image = Images.SpaghettiSquash,
            price = "50.00",
            category = FruitsCategory,
        )
    )

    add(
        Product(
            id = 8,
            title = "Cauliflower",
            image = Images.Cauliflower,
            price = "50.00",
            category = VegetablesCategory,
        )
    )
    add(
        Product(
            id = 11,
            title = "Tomato",
            image = Images.Tomato,
            price = "12.00",
            category = VegetablesCategory
        )
    )

    add(
        Product(
            id = 12,
            title = "Yellow Sweet Corn",
            image = Images.YellowSweetCorn,
            price = "60.00",
            category = VegetablesCategory,
            unit = packUnit
        )
    )

    add(
        Product(
            id = 13,
            title = "Parsley",
            image = Images.Parsley,
            price = "50.00",
            category = VegetablesCategory
        )
    )

    add(
        Product(
            id = 14,
            title = "Cilantro",
            image = Images.Cilantro,
            price = "50.00",
            category = VegetablesCategory
        )
    )

    add(
        Product(
            id = 15,
            title = "Garlic",
            image = Images.Garlic,
            price = "50.00",
            category = VegetablesCategory
        )
    )

    add(
        Product(
            id = 16,
            title = "Green Onions",
            image = Images.GreenOnions,
            price = "50.00",
            category = VegetablesCategory
        )
    )

    add(
        Product(
            id = 17,
            title = "Red Onion",
            image = Images.RedOnion,
            price = "50.00",
            category = VegetablesCategory
        )
    )

    add(
        Product(
            id = 18,
            title = "Yellow Onion",
            image = Images.YellowOnion,
            price = "50.00",
            category = VegetablesCategory
        )
    )
    add(
        Product(
            id = 19,
            title = "Red Sweet Potatoes",
            image = Images.RedSweetPotatoes,
            price = "50.00",
            category = VegetablesCategory
        )
    )
    add(
        Product(
            id = 20,
            title = "Gold Potatoes",
            image = Images.PotatoesGold,
            price = "50.00",
            category = VegetablesCategory
        )
    )

    add(
        Product(
            id = 21,
            title = "Russet Potato",
            image = Images.RussetPotato,
            price = "50.00",
            category = VegetablesCategory
        )
    )
    add(
        Product(
            id = 22,
            title = "Red Potatoes",
            image = Images.RedPotatoes,
            price = "50.00",
            category = VegetablesCategory
        )
    )

    add(
        Product(
            id = 23,
            title = "Thai Chile Pepper",
            image = Images.ThaiChilePepper,
            price = "50.00",
            category = VegetablesCategory
        )
    )

    add(
        Product(
            id = 24,
            title = "Green Serrano Pepper",
            image = Images.GreenSerranoPepper,
            price = "50.00",
            category = VegetablesCategory
        )
    )

    add(
        Product(
            id = 25,
            title = "Green Bell Pepper",
            image = Images.GreenBellPepper,
            price = "50.00",
            category = VegetablesCategory
        )
    )

    add(
        Product(
            id = 26,
            title = "Red Bell Pepper",
            image = Images.RedBellPepper,
            price = "50.00",
            category = VegetablesCategory
        )
    )
    add(
        Product(
            id = 27,
            title = "Yellow Bell Pepper",
            image = Images.YellowBellPepper,
            price = "50.00",
            category = VegetablesCategory
        )
    )

    add(
        Product(
            id = 28,
            title = "Green Cabbage",
            image = Images.GreenCabbage,
            price = "50.00",
            category = VegetablesCategory
        )
    )

    add(
        Product(
            id = 29,
            title = "Red Cabbage",
            image = Images.RedCabbage,
            price = "50.00",
            category = VegetablesCategory
        )
    )

    add(
        Product(
            id = 30,
            title = "Iceberg Lettuce",
            image = Images.IcebergLettuce,
            price = "50.00",
            category = VegetablesCategory
        )
    )

    add(
        Product(
            id = 31,
            title = "Cucumber",
            image = Images.Cucumber,
            price = "50.00",
            category = VegetablesCategory
        )
    )

    add(
        Product(
            id = 32,
            title = "Seedless Cucumber",
            image = Images.SeedlessCucumber,
            price = "50.00",
            category = VegetablesCategory,
        )
    )

    add(
        Product(
            id = 33,
            title = "Lemon",
            image = Images.Lemon,
            price = "50.00",
            category = VegetablesCategory,
        )
    )

    add(
        Product(
            id = 34,
            title = "Beans",
            image = Images.Beans,
            price = "50.00",
            category = VegetablesCategory,
        )
    )

    add(
        Product(
            id = 35,
            title = "Salmon",
            image = Images.Salmon,
            price = "50.00",
            category = FishCategory,
        )
    )

    add(
        Product(
            id = 36,
            title = "Eggplant",
            image = Images.Eggplant,
            price = "50.00",
            category = VegetablesCategory,
        )
    )

    add(
        Product(
            id = 37,
            title = "Ginger Root",
            image = Images.GingerRoot,
            price = "50.00",
            category = VegetablesCategory,
        )
    )

    add(
        Product(
            id = 38,
            title = "Yellow Peach",
            image = Images.YellowPeach,
            price = "15.00",
            category = FruitsCategory,
        )
    )

    add(
        Product(
            id = 39,
            title = "Fillet",
            image = Images.Fillet,
            price = "20.00",
            category = FishCategory,
        )
    )

    add(
        Product(
            id = 40,
            title = "Snapper Fillet",
            image = Images.SnapperFillet,
            price = "20.00",
            category = FishCategory,
        )
    )


    add(
        Product(
            id = 41,
            title = "Sole Fillet",
            image = Images.SoleFillet,
            price = "12.00",
            category = FishCategory,
        )
    )

    add(
        Product(
            id = 42,
            title = "Shrimp",
            image = Images.Shrimp,
            price = "120.00",
            category = FishCategory,
        )
    )

    add(
        Product(
            id = 43,
            title = "Steak",
            image = Images.Steak,
            price = "18.00",
            category = MeatCategory,
        )
    )


    add(
        Product(
            id = 44,
            title = "Ribeye Steak",
            image = Images.RibeyeSteak,
            price = "150.00",
            category = MeatCategory,
        )
    )
    add(
        Product(
            id = 45,
            title = "Beef Stew Meat",
            image = Images.BeefStewMeat,
            price = "160.00",
            category = MeatCategory,
        )
    )
    add(
        Product(
            id = 46,
            title = "Beef Chuck Roast",
            image = Images.BeefChuckRoast,
            price = "120.00",
            category = MeatCategory,
        )
    )

    add(
        Product(
            id = 47,
            title = "Lamb Rib Chops",
            image = Images.LambRibChops,
            price = "110.00",
            category = MeatCategory,
        )
    )

    add(
        Product(
            id = 48,
            title = "Lamb Stew Meat",
            image = Images.LambStewMeat,
            price = "110.00",
            category = MeatCategory,
        )
    )

    add(
        Product(
            id = 49,
            title = "Lamb Bone Leg",
            image = Images.LambBoneLeg,
            price = "130.00",
            category = MeatCategory,
        )
    )

    add(
        Product(
            id = 50,
            title = "Ground Beef",
            image = Images.GroundBeef,
            price = "110.00",
            category = MeatCategory,
        )
    )

    add(
        Product(
            id = 51,
            title = "Beef Liver",
            image = Images.BeefLiver,
            price = "800.00",
            category = MeatCategory,
        )
    )

    add(
        Product(
            id = 52,
            title = "Whole Chicken",
            image = Images.WholeChicken,
            price = "88.00",
            category = ChickenCategory,
        )
    )


    add(
        Product(
            id = 53,
            title = "Chicken Breast",
            image = Images.ChickenBreast,
            price = "88.00",
            category = ChickenCategory,
        )
    )

    add(
        Product(
            id = 54,
            title = "Roasted Chicken",
            image = Images.WholeRoastedChicken,
            price = "88.00",
            category = ChickenCategory,
        )
    )

    add(
        Product(
            id = 55,
            title = "Legs Chicken",
            image = Images.WholeChickenLegs,
            price = "88.00",
            category = ChickenCategory,
        )
    )

    add(
        Product(
            id = 56,
            title = "Chicken Liver",
            image = Images.ChickenLiver,
            price = "50.00",
            category = ChickenCategory,
        )
    )

    add(
        Product(
            id = 57,
            title = "Chicken Hearts",
            image = Images.ChickenHearts,
            price = "44.00",
            category = ChickenCategory,
        )
    )


}

