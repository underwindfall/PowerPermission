# PowerPermission
[ ![jCenter](https://api.bintray.com/packages/undervoid/PowerPermission/powerpermission/images/download.svg) ](https://bintray.com/undervoid/maven/Powerpermission/powerpermission/_latestVersion)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0.html)
![Android Check CI](https://github.com/underwindfall/PowerPermission/workflows/Android%20Check%20CI/badge.svg)
#### [English Documentation](https://github.com/underwindfall/PowerPermission) | 中文文档

## 目录
- [PowerPermission](#powerpermission)
  * [目录](#--)
  * [简介](#--)
  * [配置方法](#----)
    + [基础库](#---)
    + [不同的其他接口配置](#---------)
    + [总结](#--)
  * [使用方法](#----)
    + [基础方法](#----)
      - [调用单例模式](#------)
      - [Extension 模式](#extension---)
    + [RxJava/RxKotlin 使用方法](#rxjava-rxkotlin-----)
      - [基础用法](#----)
      - [结合RxBinding使用](#--rxbinding--)
    + [Coroutine 使用方法](#coroutine-----)
    + [Livedata 使用方法](#livedata-----)
    + [Rationale Interface](#rationale-interface)
      - [自定义解释界面](#-------)
      - [Choose those permissions are rational](#choose-those-permissions-are-rational)
  * [License](#license)

## 简介
`PowerPermission` 主要针对 `Android` 开发者在面对请求[RuntimePermission](https://developer.android.com/reference/java/lang/RuntimePermission)时的复杂流程的使用进行简化，来提高代码的便携效率。
> 在这个Repo已经有个示例的`application`或者你可以通过这个[链接](https://github.com/underwindfall/PowerPermission/releases)直接下载APK.
它和其他的第三方请求库不同的有以下几点:
- 可在 `AppCompatActivity` 和 `Fragment(包括ChildFragment)` 中请求
- 不固定了用户拒绝权限后的说明内容表现形式，可自行化定制
- 可自主选择那些权限再次显示解释界面
- 支持多种接口配置(RxJava2和RxJava3,Coroutines,LiveData)

<div align="left" style="display:inline">
<img width="200" height="450" src="https://raw.githubusercontent.com/underwindfall/blogAssets/master/lib/PowerPermission/example.gif">
</div>

## 配置方法
### 基础库
```groovy
implementation "com.qifan.powerpermission:powerpermission:1.2.0"
```
### 不同的其他接口配置
```groovy
implementation "com.qifan.powerpermission:powerpermission-rxjava2:1.2.0"
implementation "com.qifan.powerpermission:powerpermission-rxjava3:1.2.0"
implementation "com.qifan.powerpermission:powerpermission-coroutines:1.2.0"
implementation "com.qifan.powerpermission:powerpermission-livedata:1.2.0"
```
### 总结
|  Package Name     |     Role      | Usage |
| ------------- |-------------|-------------|
| powerpermission            | Basic core package       | implementation "com.qifan.powerpermission:powerpermission:1.2.0" |
| powerpermission-rxjava2    | Support RxJava2          | implementation "com.qifan.powerpermission:powerpermission-rxjava2:1.2.0" |
| powerpermission-rxjava3    | Support RxJava3          | implementation "com.qifan.powerpermission:powerpermission-rxjava3:1.2.0" |
| powerpermission-coroutines | Support Kotlin Coroutine | implementation "com.qifan.powerpermission:powerpermission-coroutines:1.2.0" |
| powerpermission-livedata   | Support Android LiveData | implementation "com.qifan.powerpermission:powerpermission-livedata:1.2.0" |

## 使用方法

### 基础方法
#### 调用单例模式
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
#### Extension 模式
- 在Activity请求
```kotlin
 askPermissions(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CALENDAR
        ) { permissionResult ->
            //do whatever you want
        }
```
- 在Fragment请求
```kotlin
 askPermissions(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CALENDAR
        ) { permissionResult ->
            //do whatever you want
        }
```
- 在ChildFragment中请求
```kotlin
 askPermissions(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CALENDAR
        ) { permissionResult ->
            //do whatever you want
        }
```
### RxJava/RxKotlin 使用方法
#### 基础用法
```kotlin
 simpleRequestButton.setOnClickListener {
            askPermissionsRx(Manifest.permission.CAMERA)
}
```
#### 结合RxBinding使用
```kotlin
 rxBindingRequestButton.clicks()
            .throttleFirst(1L, TimeUnit.SECONDS)
            .flatMap {
                askPermissionsRx(Manifest.permission.CAMERA)
            }
```

### Coroutine 使用方法
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

### Livedata 使用方法
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
#### 自定义解释界面
在 `PowerPermission` 中它包含了一个 `interface` 叫做`RationaleDelegate`, 你可以把它当作一个桥梁来创造你的`Custom view`。
它提供了两个公开的方法,
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
PS: `PowerPermission` 内置一个默认的 `DialogRationaleDelegate.kt` 来展示android经典的dialog界面向用户解释为什么我们需要这个`permission`的原因。

#### Choose those permissions are rational
- RationaleData
此外他还有个`data class`被用于向用户选择那些`permission`请求应该显示界面和相应的解释信息是什么。
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