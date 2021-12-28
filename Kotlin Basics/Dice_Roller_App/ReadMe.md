# Dice Roller App
Two player dice roller game where players are expected to click the dices to generate random numbers on top faces of the dices. Whenever the lucky number 6 is achieved, the corresponding player is greeted with a congratulatory message in a SnackBar.

## Screenshots
<img src="https://github.com/shining-armour/MyAndroidLearnings/blob/main/Kotlin%20Basics/Dice_Roller_App/screenshots/Screenshot_1.png" width="250" height="400">   <img src="https://github.com/shining-armour/MyAndroidLearnings/blob/main/Kotlin%20Basics/Dice_Roller_App/screenshots/Screenshot_2.png" width="250" height="400">  <img src="https://github.com/shining-armour/MyAndroidLearnings/blob/main/Kotlin%20Basics/Dice_Roller_App/screenshots/Screenshot_3.png" width="250" height="400">

## Updated Screenshots
<img src="https://github.com/shining-armour/MyAndroidLearnings/blob/main/Kotlin%20Basics/Dice_Roller_App/screenshots/Screenshot_4.png" width="250" height="400">    <img src="https://github.com/shining-armour/MyAndroidLearnings/blob/main/Kotlin%20Basics/Dice_Roller_App/screenshots/Screenshot_5.png" width="400" height="250"> 

# Learnings

* Classes and Objects
  - A class is a blueprint and an instance is the actual “thing". A class is like architectural plans for a bridge, and the Golden Gate bridge is an instance of those plans.
  
* Call the random() function on an IntRange to generate a random number: *(1..6).random()*

* When we declare any variable as lateinit, we are assuring that we will initialize it before making use of it anywhere inside the code.
  
* Inflate layout using **viewBinding** [recommended]
s
* Use **setOnClickListener** to handle click events on views

* Prevent data loss from lifecycle changes during rotation by overriding **onSaveInstanceState**.
  - Save data using *outState.put<DataType>(KEY, VALUE)*
  - Retrieve data using *savedInstanceState.get<DataType>(KEY)*

* Use **companion object** to initialize the constants

* String interpolation in kotlin: "Some variable with $myVariable value" [Note: Avoid surrounding myVariable with curly braces. Curly braces are used only for expressions.]

* Strings that await for variable values from code [eg. Score: %1$d (or %d), Score: %s] can be assigned using *getString(R.string.res_name, valuePassed)*

* Visibility property can have values like: 
  - visible = default
  - invisible = hidden & takes up space
  - gone =  hidden & doesn't take space 

* If you do not explicitly define access modifier then by default, it will be **public**.

* *when* block is the kotlin's version of switch case.

* Set Drawable image to ImageView using *imageView.setImageResource(drawableResource)*

* Use control flow statements like *if / else* expressions or *when* expressions to handle different cases in your app, for example, showing different images under different circumstances.



