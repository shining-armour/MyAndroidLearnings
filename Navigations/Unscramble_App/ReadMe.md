# UnScramble App
A Single Fragment Game App where the user have to guess the scrambled words. The app displays one scrambled word at a time, and the player has to guess the word using all the letters from the scrambled word. The player scores 20 points if the word is correct, otherwise the player can try any number of times. 

# Screenshots
<img src="https://github.com/shining-armour/MyAndroidLearnings/blob/main/Navigations/Unscramble_App/screenshots/Screenshot_1.png" width="250" height="450"> <img src="https://github.com/shining-armour/MyAndroidLearnings/blob/main/Navigations/Unscramble_App/screenshots/Screenshot_2.png" width="250" height="450"> 

<img src="https://github.com/shining-armour/MyAndroidLearnings/blob/main/Navigations/Unscramble_App/screenshots/Screenshot_3.png" width="250" height="450"> <img src="https://github.com/shining-armour/MyAndroidLearnings/blob/main/Navigations/Unscramble_App/screenshots/Screenshot_4.png" width="450" height="250">

# Learnings

* A well-designed app architecture helps you scale your app and extend it with additional features in the future.

* This app is architected in the following way. MainActivity contains a GameFragment, and the GameFragment accesses information about the game from the GameViewModel.

* Architectural principles:-
  - **Separation of concerns** : App should be divided into classes, each with separate responsibilities.
  - **Driving UI from a model** : UI should be controlled with the help of models. Models are components that are responsible for handling the data for an app. They're independent from the Views and app components in your app, so they're unaffected by the app's lifecycle and the associated concerns.
    
* Android Architecture components :- UI Controller, ViewModel, LiveData and Room. 
  - **UI controller (F/A)** - Activities and fragments draw views and data to the screen and respond to the user events. 
  - **ViewModel** - ViewModel hold and process all the data needed for the UI. It should never access your view hierarchy (like view binding object) or hold a reference to the activity or the fragment.
  - **LiveData** - LiveData is an observable data holder class which is lifecycle-aware.  
  - Room is not implemented in this app

* **Lifecyle-aware components**
  - ViewModel: Helps to create, store and retrieve data and communicates with other components belonging to the same lifecycle. 
  - Lifecycle Owner: It’s an interface implemented by activity and fragment, to observe changes to the lifecycle of owners.
  - LiveData: Allows observing changes in data across diff components in the same lifecycle.

* **Get started with ViewModel & LiveData**

  - Add viewmodel & livedata dependencies in your app's build.gradle:
    - *implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0'*
    - *implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.4.0'*
  - Create your custom viewmodel class that extends ViewModel class.
    - *class MyViewModel : ViewModel() { // data goes here }*
  - Create a reference of the ViewModel inside fragment/activity using property delegate.
    - *private val viewModel: MyViewModel by <delegate-class>()*
    - delegate class can be -> *activityViewModel, viewModel, customViewModelLazy*   
  - Using MutableLiveData, LiveData and backing property, create the properties that will hold the real-time data.
    - *private val _myPrivateMutableProperty = MutableLiveData(0)*
    - *val myExposedImmutableProperty: LiveData<Int>*  
          - *get() = _myPrivateMutableProperty*
  - Create methods to change the values of those properties based on certain conditions inside the viewmodel class.
    - *private fun changeValue() { if (...) _myPrivateMutableProperty.value = ...}*
    
  - **There are two ways by which you can update the UI based on LiveData value**:
    -1. Observe the LiveData using viewLifecycleOwner 
      - *viewModel.myExposedImmutableProperty.observe(viewLifecycleOwner, {*
         - *newData -> binding.myTextView.text = newData*
          - *})*
    -2. Use Databinding with binding expressions
      - Enable data binding and add kotlin-kapt plugin in app's build.gradle:
        - *buildFeatures { dataBinding true }*
        - *id 'kotlin-kapt'*
      - Wrap your root ViewGroup inside <layout> tag.
      - Inside <data> tag, define viewModel variable having name and datatype (path of viewModel class). 
        - *<layout xmlns:android="http://schemas.android.com/apk/res/android"*
          - *xmlns:app="http://schemas.android.com/apk/res-auto"*
          - *xmlns:tools="http://schemas.android.com/tools">*
          - *<data>*
          - *<variable*
          - *name="mylayoutViewModel"*
          - *type="com.example.myapp.MyViewModel" />*
          - *</data>*
          - *Rest of the layout....*
        - *</layout>*
      - Assign the viewModel instance that you created earlier in the fragment using property delegation to the data variable for the viewModel that you created.
        - *binding.mylayoutViewModel = viewModel*
        - Now, you don't need to observe the livedata values and this will reduce a lot of boilerplate code.
      - Binding expressions are written within the layout in the attribute properties (such as android:text) referencing the layout properties. When a change occurs in the LiveData, the ‘DB Library' will run your binding expressions (and thus updates the views).
        - *android:text="@{viewModel.myExposedImmutableProperty}"*  
        - *<string name="exposed_property">Property Value: %d</string> -> android:text="@{@string/score(viewModel.myExposedImmutableProperty)}"*
      
* To protects the app data inside the ViewModel from unwanted and unsafe changes by external classes and also allow external callers to safely access its value.
  - **Inside the ViewModel**
    - Data should be editable, so they should be **private** and **var**. 
    - Always prefix your private properties with an underscore.
  - **Outside the ViewModel**
    - Data should be readable, but not editable, so the data should be exposed as **public** and **val**. Default visibility modifier in Kotlin is public.
  
* **ViewModel lifecycle**
  - ViewModel is alive as long as the scope of the activity or fragment is alive. 
  - A ViewModel is not destroyed if its owner is destroyed for a configuration change, such as screen rotation. The new instance of the owner reconnects to the existing ViewModel instance.  
  - ViewModel allows the app's data to survive configuration changes without having to use saveInstanceState.                                                                                                                                                                                                                                                                                               
  
* **Some characteristics of LiveData**:
  - LiveData holds data; LiveData is a wrapper that can be used with any type of data. 
  - LiveData is observable, which means that an observer is notified when the data held by the LiveData object changes. 
  - LiveData is lifecycle-aware, meaning it only updates observers that are in an active lifecycle state. So the observer in the GameFragment will only be notified when the GameFragment is in STARTED or RESUMED states.
  
* **MutableLiveData** is the mutable version of the LiveData, that is, the value of the data stored within it can be changed.

* **viewLifecycleOwner** represents the Fragment's View lifecycle. This parameter helps the LiveData to be aware of the GameFragment lifecycle and notify the observer only when the GameFragment is in active states (STARTED or RESUMED).

* Errors are set for the TextInputLayout not for the EditText.

# Questions

* **When to use lateinit?**
  - If you guarantee that you will initialize the property before using it, you can declare the property with lateinit. Memory is not allocated to the variable until it is initialized. If you try to access the variable before initializing it, the app will crash.
    
* **How do I persist the dialog after screen rotation?**
  - To prevent the dialog dismissal on screen rotation, use **DialogFragment**.
  
* **setCancelable(false) vs setCanceledOnTouchOutside(false)?**
  -  setCanceledOnTouchOutside(false) for preventing the dismiss on touching outside of alert dialog.
  -  setCancelable(false) is used for preventing the dismiss on pressing the back button.
  
* **How to prevent dialog from dismissing when user touch outside of the dialog box? why setCancelable(false) is not working?**
  - It turns out you have to call setCancelable(false) on the DialogFragment itself, not the inner Dialog that it holds.
  - Meaning, inside the onCreateDialog(), set isCancelable = false independently not on the dialog.
  
* **How does property delegation using *by* keyword works?**
  - Property delegation in Kotlin helps you to handoff the getter-setter responsibility to a different class. This class (called delegate class) provides getter and setter functions of the property and handles its changes. A delegate property is defined using the *by* clause and a delegate class instance.
  - Syntax for property delegation -> *var <property-name> : <property-type> by <delegate-class>()*
  - In your app, if you initialize the view model using default GameViewModel constructor, like: *private val viewModel = GameViewModel()*
  - Then the app will lose the state of the viewModel reference when the device goes through a configuration change. For example, if you rotate the device, then the activity is destroyed and created again, and you'll have a new view model instance with the initial state again.
  - Instead, use the property delegate approach and delegate the responsibility of the viewModel object to a separate class called viewModels.
  - That means when you access the viewModel object, it is handled internally by the delegate class, viewModels. The delegate class creates the viewModel object for you on the first access, and retains its value through configuration changes and returns the value when requested.
  
* **Delegate classes : viewModels() vs activityViewModels() vs customViewModelLazy()?**
  - viewModels<>() -> Gives you the ViewModel *scoped to the current fragment*. [eg. private val viewModel by viewModels<MyViewModel>()]
  - activityViewModels<>() -> Gives you the ViewModel *scoped to the current Activity* therefore it is alive until the activity is destroyed. If you get the same ViewModel by activityViewModels in another fragment you will get the same instance. [eg. private val activityViewModel by activityViewModels<MyViewModel>()]
  - createViewModelLazy<>() ->  You can give your own ViewModelStore so that you *create your own scope*. [eg. private val customScopedViewModel by createViewModelLazy(MyViewModel::class, { viewModelStore // Or your custom ViewModelStore here })]  
  - Rule of thumb is if you want to share the same instance of viewModel across multiple fragments, use activityViewModel. If you want different instances for different fragments, use viewModel.
  
* **What is meant by trailing lambda syntax?**
  - When the last argument being passed in is a function, you could place the lambda expression outside the parentheses. Eg. *setNegativeButton(CharSequence!, DialogInterface.OnClickListener!)*

* **What is a Context?**
  - Context refers to the context or the current state of an application, activity, or fragment. It contains the information regarding the activity, fragment or application. Usually it is used to get access to resources, databases, and other system services.
  
* **viewBinding vs dataBinding?**
  - ViewBinding (one-way binding) -> Only binding views to code.
  - DataBinding (two-way binding) -> Binding data (from code) to views + ViewBinding (Binding views to code)
  - We can say, viewBinding is a subset of DataBinding. DataBinding comes with additional features apart from the common viewBinding features.
  - Important points of difference between these two:
    - With view binding, the layouts do not need a layout tag.
    - You can't use viewbinding to bind layouts with data in xml (No binding expressions, no BindingAdapters nor two-way binding with viewbinding)
    - The main advantages of viewbinding are speed and efficiency. It has a shorter build time because it avoids the overhead and performance issues associated with databinding due to annotation processors affecting databinding's build time.
    - View Binding library is faster than Data Binding library as it is not utilising annotation processors underneath, and when it comes to compile time speed View Binding is more efficient. 
    - The one and only function of View Binding is to bind the views in the code. While Data Binding offers some more options like Binding Expressions, which allows us to write expressions the connect variables to the views in the layout. 
    - Data Binding library works with Observable Data objects, you don't have to worry about refreshing the UI when underlying data changes. 
    - Data Binding library provides us with Binding Adapters. 
    - Data Binding library provides us with Two way Data Binding, this is a technique of binding your objects to xml layouts, so that both object and layout can send data to each other.
    - The main advantage of using data binding is, it lets you remove many UI framework calls in your activities, making them simpler and easier to maintain. This can also improve your app's performance and help prevent memory leaks and null pointer exceptions.
  
* **createViewModelLazy() useCase?**
  
* **How to write tests for livedata and viewModel?**