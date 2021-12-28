# BirthDay Card App
Single Activity App demonstrating basic usage of TextView and ImageView in a Constraint Layout

# Screenshot
<img src="https://github.com/shining-armour/MyAndroidLearnings/blob/main/Kotlin%20Basics/Birthday_Card_App/screenshots/Screenshot_1.png" width="250" height="400">

# Learnings

* Inflating a layout is the process of adding a view (.xml) to activity on runtime. 
Types:
*Implicit Inflation* - The Android OS will inflate the view for you 
*Explicit Inflation* - You explicitly inflate the view using LayoutInflater
  
* ViewGroup serves as a container for View objects, and is responsible for arranging the View objects within it.

* If you find the attributes of multiple views (TextView, EditText, etc) to have same values then refrain from writing same properties multiple times. Achieve commonality by creating style tags
1. Right-click on any view with common attributes [ Note: Do not select text and right click ]
2. Refractor -> style 
3. Select attributes you want to move to style tag
4. use style property in your xml file. [ eg. style = @style/style_tag_name ]

* Adding custom font family
1. Go to attribute panel of the TextView
2. Search for fontfamily
3. Click on rightmost vertical symbol. A Dialog will appear
4. plus icon -> more fonts -> select desired font -> check add font to project -> OK

* Minimum SDK is the minimum version of Android that your app can run on

* A graphic that can be drawn to the screen is generally referred as a *Drawable*.

* ImageViews should have a content description to help make your app more accessible.

# Questions

**Why can't I just use the background property for the root ViewGroup and set that as drawable image? What is the need of explicitly creating an ImageView?**

Ans - While setting the background with drawable image does work however, you do not get functionalities like scaleType or adjusting height-width of image w.r.t other views. 

**Why should I use string resources instead of hard-coded strings?**

Ans - It makes your app easier to translate. It allows you to reuse the same string in multiple places in your app.


