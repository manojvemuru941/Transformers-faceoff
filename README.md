# Transformers
The Transformers are at war and you are in charge of settling the score! 
This application evaluates who wins a fight between the Autobots and the Decepticons.

# Project Architecture - MVVM  
![Alt text](https://google-developer-training.gitbooks.io/android-developer-advanced-course-practicals/images/14-1-a-room-livedata-viewmodel/dg_architecture_comonents.png?raw=true "MVVM")

## Componenets
Because all the components interact, you will encounter references to these components throughout this practical, so here is a short explanation of each.

## Entity 
Class Names : BotModel<br />
In the context of Architecture Components, the entity is an annotated class that describes a database table.
SQLite database: On the device, data is stored in a SQLite database. The Room persistence library creates and maintains this database for you.

## DAO
Interface Names : BotDao<br />
Short for data access object . A mapping of SQL queries to functions. You used to have to define these queries in a helper class. When you use a DAO, your code calls the functions, and the components take care of the rest.

## Room database
Class Names : AppDBService<br />
Database layer on top of a SQLite database that takes care of mundane tasks that you used to handle with a helper class. The Room database uses the DAO to issue queries to the SQLite database based on functions called.

## Model
Class Names : Model<br />
A class that you create for managing multiple data sources. In addition to a Room database, the Model could manage remote data sources such as a web server.

## ViewModel
Class Names : MainViewModel, DetailViewModel<br />
Provides data to the UI and acts as a communication center between the Model and the UI. Hides the backend from the UI. ViewModel instances survive device configuration changes.

## LiveData
A data holder class that follows the observer pattern, which means that it can be observed. Always holds/caches latest version of data. Notifies its observers when the data has changed. Generally, UI components observe relevant data. LiveData is lifecycle aware, so it automatically manages stopping and resuming observation based on the state of its observing activity or fragment.

## UI
Class Names : MainActivity, FragmentList, ItemDetailActivity, ItemDetailFragment
### MainActivity 
A UI class that handles input actions for create transoformer Button and start a war button and requests MainViewModel to create web service for create transformer and insert Transformer into database.

### FragmentList
A Part of list of Transformer UI that handles list transoformers to populate to user using [RecyclerView](https://developer.android.com/reference/android/support/v7/widget/RecyclerView) and [RecyclerView.Adapter](https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter)

![Alt text](https://raw.githubusercontent.com/manojvemuru941/Practice/master/TransformersAE/screenshots/Main%20Screen.png)

### ItemDetailActivity
A UI class that handles Viewing, Creating and Updating individual transformers

### ItemDetailFragment
A Part of UI of Viewing, Creating and Updating individual transformers which handles showing and handling criteria views

![Alt text](https://raw.githubusercontent.com/manojvemuru941/Practice/master/TransformersAE/screenshots/Create.png)
![Alt text](https://raw.githubusercontent.com/manojvemuru941/Practice/master/TransformersAE/screenshots/Update.png)
![Alt text](https://raw.githubusercontent.com/manojvemuru941/Practice/master/TransformersAE/screenshots/View1.png)
![Alt text](https://raw.githubusercontent.com/manojvemuru941/Practice/master/TransformersAE/screenshots/View2.png)


