# PowerPermission
[ ![jCenter](https://api.bintray.com/packages/qifan/maven/powerpermission/images/download.svg) ](https://api.bintray.com/packages/qifan/maven/powerpermission/_latestVersion)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0.html)
![Android Check CI](https://github.com/underwindfall/PowerPermission/workflows/Android%20Check%20CI/badge.svg)
#### English Documentation | [中文文档](https://github.com/underwindfall/PowerPermission/blob/master/README_ZH.md)

## Table of Contents
- [PowerPermission](#powerpermission)
  * [Table of Contents](#table-of-contents)
  * [Introducation](#introducation)
  * [How to Download](#how-to-download)
    + [Basic](#basic)
    + [Other API Support](#other-api-support)
    + [OverView](#overview)
  * [How to use](#how-to-use)
    + [Simple Usage](#simple-usage)
      - [Singleton](#singleton)
      - [Extension](#extension)
    + [RxJava/RxKotlin](#rxjava-rxkotlin)
      - [Basic Usage](#basic-usage)
      - [Use with RxBinding](#use-with-rxbinding)
    + [Coroutine](#coroutine)
    + [Livedata](#livedata)
    + [Rationale Interface](#rationale-interface)
      - [Custom View](#custom-view)
      - [Choose those permissions are rational](#choose-those-permissions-are-rational)
  * [License](#license)

## Introducation
`PowerPermission` is a library to simplify process of demanding [RuntimePermission](https://developer.android.com/reference/java/lang/RuntimePermission).
> You find an `example application` in this Repo or downloading directly [here](https://github.com/underwindfall/PowerPermission/releases).
Here are some different points compare with other libraries:
- support asking permissions in `AppCompatActivity` and `Fragment(include ChildFragment)`
- support custom rational view after user refuse permission
- support ability to choose permissions to display rational views
- support different interface (RxJava2,RxJava3,Coroutines,LiveData)

<div align="left" style="display:inline">
<img width="200" height="450" src="https://raw.githubusercontent.com/underwindfall/blogAssets/master/lib/PowerPermission/example.gif">
</div>

## How to Download
### Basic
```groovy
implementation "com.qifan.powerpermission:powerpermission:1.2.0"
```
### Other API Support
```groovy
implementation "com.qifan.powerpermission:powerpermission-rxjava2:1.2.0"
implementation "com.qifan.powerpermission:powerpermission-rxjava3:1.2.0"
implementation "com.qifan.powerpermission:powerpermission-coroutines:1.2.0"
implementation "com.qifan.powerpermission:powerpermission-livedata:1.2.0"
```
### OverView
|  Package Name     |     Role      | Usage |
| ------------- |-------------|-------------|
| powerpermission            | Basic core package       | implementation "com.qifan.powerpermission:powerpermission:1.2.0" |
| powerpermission-rxjava2    | Support RxJava2          | implementation "com.qifan.powerpermission:powerpermission-rxjava2:1.2.0" |
| powerpermission-rxjava3    | Support RxJava3          | implementation "com.qifan.powerpermission:powerpermission-rxjava3:1.2.0" |
| powerpermission-coroutines | Support Kotlin Coroutine | implementation "com.qifan.powerpermission:powerpermission-coroutines:1.2.0" |
| powerpermission-livedata   | Support Android LiveData | implementation "com.qifan.powerpermission:powerpermission-livedata:1.2.0" |

## How to use
### Simple Usage
#### Singleton
```kotlin
  PowerPermission.init()
            .requestPermissions(
                context = this@ExampleActivity,
                permissions = *arrayOf(
                    Manifest.permission.CAMERA
                )
            ) { permissionResult ->
                when {
                    permissionResult.hasAllGranted() -> {
                        doPermissionAllGrantedWork(permissionResult.granted())
                    }
                    permissionResult.hasRational() -> {
                        doPermissionReasonWork(permissionResult.rational())
                    }
                    permissionResult.hasPermanentDenied() -> {
                        doPermissionPermanentWork(permissionResult.permanentDenied())
                    }
                }
            }
```
#### Extension
- Require in Activity
```kotlin
 askPermissions(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CALENDAR
        ) { permissionResult ->
            //do whatever you want
        }
```
- Require in Fragment
```kotlin
 askPermissions(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CALENDAR
        ) { permissionResult ->
            //do whatever you want
        }
```
- Require in ChildFragment
```kotlin
 askPermissions(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CALENDAR
        ) { permissionResult ->
            //do whatever you want
        }
```

### RxJava/RxKotlin
#### Basic Usage
```kotlin
 simpleRequestButton.setOnClickListener {
            askPermissionsRx(Manifest.permission.CAMERA)
}
```
#### Use with RxBinding
```kotlin
 rxBindingRequestButton.clicks()
            .throttleFirst(1L, TimeUnit.SECONDS)
            .flatMap {
                askPermissionsRx(Manifest.permission.CAMERA)
            }
```

### Coroutine
```kotlin
    simpleRequestButton.setOnClickListener {
            launch {
                val result = awaitAskPermissions(
                    Manifest.permission.CAMERA,
                    rationaleDelegate = dialogRationaleDelegate
                )
                //do something for this permission result
               // handlePermissionRequest(result)
            }
        }
```

### Livedata
```kotlin
simpleRequestButton.setOnClickListener {
            observeAskPermissions(
                Manifest.permission.CAMERA,
                rationaleDelegate = dialogRationaleDelegate
            ).observe(this, Observer{ result->
               //do something for this permission result
               // handlePermissionRequest(result)
            })
        }
```

### Rationale Interface
#### Custom View
In `PowerPermission` it have a `interface` which called `RationaleDelegate`, it should be used as bridge to create your proper  
delegation class to implement this `interface`.
It provide two basic public declaration functions,
- displayRationale
```kotlin
//aims to display a view to explain reason to user why request permissions
    fun displayRationale(
        vararg permission: Permission,
        message: String,
        actionCallback: RationaleActionCallback
    )
```
- onDismissView
```kotlin
//aims to simply disappear view and do some clean work etc.
 fun onDismissView()
```
PS: `PowerPermission` has already provided a class called `DialogRationaleDelegate.kt` to display a classic dialog view you can take a look at it and find more inspiration.

#### Choose those permissions are rational
- RationaleData
It's a `data class` used as choose which permission or permissions to display rationale view.
And what kind of message will be displayed in your rational view.
example:
```kotlin
RationaleData(
            rationalPermission = Manifest.permission.CAMERA,
            message = message
        )
RationaleData(
            rationalPermissions = listOf(Manifest.permission.CAMERA),
            message = message
        )
```

## License
    Copyright (C) 2020 Qifan Yang
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
