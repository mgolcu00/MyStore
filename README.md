# MyStore

## The UI Structure

```
features
|
|__ task 
|   |__ TaskScreen.kt
|   |__ TaskViewModel.kt
|   |__ TaskComponents.kt
|   |__ detail
|   |   |__ TaskDetailScreen.kt
|   |   |__ TaskDetailViewModel.kt (optional)
|   |   |__ TaskDetailComponents.kt (optional)
|
|__ notes
|   |__ NoteScreen.kt
|   |__ NoteViewModel.kt
|   |__ NoteComponents.kt
|   |__ detail
|   |   |__ NoteDetailScreen.kt
|   |   |__ NoteDetailViewModel.kt (optional)
|   |   |__ NoteDetailComponents.kt (optional)
|
common
|
|__ ui
|   |__ components
|   |   |__ BottomBar.kt
|   |   |__ TopBar.kt
|   |   |__ Loading.kt
|   |   |__ Error.kt
|   |   |__ Empty.kt
|   |   |__ Animations.kt
|   |   |__ ...
|   |__ theme
|   |   |__ Colors.kt
|   |   |__ Typography.kt
|   |   |__ Shapes.kt
|   |   |__ ...
|   |__ utils
|   |   |__ Extensions.kt
|   |   |__ ...
|   |__ navigation
|   |   |__ Navigation.kt
|   |   |__ Animation.kt
|   |   |__ ...
|   |__ ...
|
data
|
|__ local
|   |__ database
|   |   |__ AppDatabase.kt
|   |   |__ TaskDao.kt
|   |   |__ NoteDao.kt
|   |   |__ ...
|   |__ preferences
|   |   |__ PreferenceStorage.kt
|   |   |__ SharedPreferenceStorage.kt
|   |   |__ ...
|   |__ ...
|
|__ remote
|   |__ api
|   |   |__ ApiService.kt
|   |   |__ ...
|   |__ models
|   |   |__ TaskDto.kt
|   |   |__ NoteDto.kt
|   |   |__ ...
|   |__ ...
|
|__ repository
|   |__ TaskRepository.kt
|   |__ NoteRepository.kt
|   |__ ...
|
|__ models
|   |__ Task.kt
|   |__ Note.kt
|   |__ ...
|
|__ ...
|
di
|
|__ AppComponent.kt
|__ AppProvider.kt
|__ ...
|
utils
|
|__ ...
|
App.kt
```

