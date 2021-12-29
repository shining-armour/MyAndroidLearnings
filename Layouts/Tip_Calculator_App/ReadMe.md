# Tip Calculator App
A single activity app to calculate the tip amount based on user's experience at restaurants.

# Screenshots
<img src="" width="250" height="450"> <img src="" width="250" height="450"> <img src="" width="250" height="450"> <img src="" width="450" height="250">

# Learnings

* Material Design - TextInputEditText nested in TextInputLayout, RadioGroup with RadioButtons, SwitchMaterial, MaterialButton, etc
* Adding vector icons to layout.
* Adaptive icons were introduced to the Android platform in API 26. They are made up of a foreground and background layer that follow specific requirements, so that your app icon looks high-quality on a range of devices with different OEM masks.  
* Changing launcher icons by adding foreground and background image assets.

* Using **inputType** attribute to limit what type of text the user can input into an EditText field.

* Using **checkedRadioButtonId** attribute of a RadioGroup to find which RadioButton is selected.

* You can use string parameters like %s to create dynamic strings that can still be easily translated into other languages.
  
* Using *kotlin.math.ceil(number)* for rounding up
  
* Formatting amount to currency -> *NumberFormat.getCurrencyInstance().format(amount)*
  
* Hiding keyboard using **InputMethodManger**
  *val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
  imm.hideSoftInputFromWindow(view.windowToken, 0)*
  
* Regular Expressions for checking number in string : *string.contains("[0-9]".toRegex())*
  
* isNullOrEmpty() vs isNullOrBlank()
  - val str = "  "
  - str.isNullOrEmpty() ->  false  
  - str.isNullOrBlank() ->  true
  
* **importantForAccessibility** specifies whether the ImageView is important for screen readers or not.

* [Material Color Tool to select colors for your app theme.](https://material.io/resources/color/#!/?view.left=0&view.right=0)

* Dark theme can reduce power usage and make your app easier to read in low light.

* [Espresso](https://developer.android.com/training/testing/espresso/basics) is a fundamental component of instrumentation tests. It enables interaction with UI components using code.

# Questions

**Why does device keyboard does not collapse even when user clicks done button? Why does the done button changes to next line button instead?**

Ans - Use imeOptions and inputType to provide the user with the right kind of keyboard.


  
  

  