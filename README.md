# Neobis_Android_Chapter8


## Описание проекта

Данный проект представляет собой приложение: " Chapter8", представляет собой программное обеспечение призвано обеспечить пользователям беспроблемный 
и интуитивно понятный опыт при просмотре и покупке товаров. Кроме того, оно включает в себя функцию профиля пользователя, которая позволяет пользователям 
управлять своей личной информацией, включая проверку номера телефона и редактирования информации о товаре. 

Цель проекта состоит в том, чтобы спроектировать и создать структуру продуктового приложения, которое будет отображать список товаров. 
В данном приложении использовался навигация между фрагментами осуществляется с помощью Navigation Component.
А списки реализованы RecyclerView 
Так же использовались:
- RecyclerView
    - Adapter. Паттерн Adapter
    - Card layout
    - Bottom navigation с Bottom Navigation View
    - On Item Click
- Fragment, lifecycle
- Glide
-  Архитектура MVVM
- Coroutines
 - Архитектура ViewModel
    - LiveData и MutableLiveData
- REST API
    - Queries(POST, GET, PUT, DELETE)
    - Server Response
    - JSON and GSON
-  Retrofit
- DiffUtils для быстрого обновления RecyclerView
- Архитектура ViewModel
- API от бэкэндера
- SingleLiveEvent
- PinView
- Snackbar
- Маски для номера телефона 
- Токен, Interceptor
- ViewPager2, TabLayout
- Отправка медиафайлов в бэк Multipart.Body
- SharedPreferences

## Установка проекта

**MacOS:**
1. Установить Android Studio https://developer.android.com/studio/
2. Открыть проект или клонировать (клонировать этот проект по инструкции https://www.jetbrains.com/idea/guide/tutorials/creating-a-project-from-github/clone-from-github/)
3. Для запуска проекта в Android Studio нажмите на зеленый треугольник на панели сверху справа


**Windows:**
1. Установить Android Studio https://developer.android.com/studio/
2. Открыть проект или клонировать (клонировать этот проект по инструкции https://www.jetbrains.com/idea/guide/tutorials/creating-a-project-from-github/clone-from-github/)
3. Для запуска проекта в Android Studio нажмите на зеленый треугольник на панели сверху справа

## Функционал проекта 

Цель проекта состоит в том, чтобы спроектировать и создать структуру приложения для товаров. 
- Пользователь авторзовывается, проходит авторизацию по номеру телефона, получает код. Далее осуществляет полную регистрацию.
- Отображение товаров реализовано с помощью RecyclerView
- Для каждой  карточки товара необходимо указать все нужные заголовки и фото.
- При нажатии на каждый элемент должен открываться просмотр с подробной информацией об этом товаре.
- Есть список общих товаров, которые добавили все пользователи
- Есть список понравившихся и своих добавленных товаров
Мы можем нажать на карточку любого и провалиться в подробную информацию. Есть возможность залайкать новость, нажав на лайк. Из лайков мы можем убрать его из списка.
Так же можно нажать на стрелку в верхнем левом углу и вернуться назад к начальному списку

<img width="355" alt="Снимок экрана 2023-10-17 в 11 52 03" src="https://github.com/lizazueva/Neobis_Android_Chapter8/assets/56483500/0bed1eff-3af1-47bf-a48c-79dccce2f942">
<img width="351" alt="Снимок экрана 2023-10-17 в 11 52 34" src="https://github.com/lizazueva/Neobis_Android_Chapter8/assets/56483500/7199beb4-bd40-48f3-834c-d9915e45ff72">
<img width="354" alt="Снимок экрана 2023-10-17 в 11 53 59" src="https://github.com/lizazueva/Neobis_Android_Chapter8/assets/56483500/ca6388a2-bc5a-4f88-b796-e9a9ec841ac8">
<img width="346" alt="Снимок экрана 2023-10-17 в 11 54 46" src="https://github.com/lizazueva/Neobis_Android_Chapter8/assets/56483500/6ad33021-2dbe-4444-adc3-bbfcb8f0c925">
<img width="347" alt="Снимок экрана 2023-10-17 в 11 55 00" src="https://github.com/lizazueva/Neobis_Android_Chapter8/assets/56483500/e411a3ce-4a29-4882-8102-48a75d4b046d">
<img width="355" alt="Снимок экрана 2023-10-17 в 11 54 37" src="https://github.com/lizazueva/Neobis_Android_Chapter8/assets/56483500/f5e677a8-3d17-4cbf-8313-8b5c9eeafc10">
<img width="342" alt="Снимок экрана 2023-10-17 в 11 56 15" src="https://github.com/lizazueva/Neobis_Android_Chapter8/assets/56483500/06c24c9d-e63c-4de8-ab29-75ee5c4eaa3e">
<img width="354" alt="Снимок экрана 2023-10-17 в 11 <img width="346" alt="Снимок экрана 2023-10-17 в 11 59 23" src="https://github.com/lizazueva/Neobis_Android_Chapter8/assets/56483500/a1c30eeb-331b-44ca-9e44-377baf53d322">
<img width="343" alt="Снимок экрана 2023-10-17 в 11 57 48" src="https://github.com/lizazueva/Neobis_Android_Chapter8/assets/56483500/fe5d366d-2dd3-46f5-9516-fc1d50c784fa">
<img width="341" alt="Снимок экрана 2023-10-17 в 11 58 24" src="https://github.com/lizazueva/Neobis_Android_Chapter8/assets/56483500/9a92baf5-df37-4afb-85ef-bf1b735a0d6c">



## Автор проекта

Автор проекта: lizazazu@gmail.com


