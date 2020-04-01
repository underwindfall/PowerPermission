# PowerPermission
#### [English Documentation](https://github.com/underwindfall/PowerPermission) | 中文文档

## 简介
`PowerPermission` 主要针对 `Android` 开发者在面对请求[RuntimePermission](https://developer.android.com/reference/java/lang/RuntimePermission)时的复杂流程的使用进行简化，来提高代码的便携效率。
它和其他的第三方请求库不同的有以下几点:
- 可在 `AppCompatActivity` 和 `Fragment(包括ChildFragment)` 中请求
- 不固定了用户拒绝权限后的说明内容表现形式，可自行化定制
- 可自主选择那些权限再次显示解释界面
- 支持多种接口配置(RxJava2和RxJava3,Coroutines,LiveData)

## 配置方法
### 基础库
```groovy
implementation "com.qifan.powerpermission:powerpermission:1.0.0"
```
### 不同的其他接口配置
```groovy
implementation "com.qifan.powerpermission:powerpermission-rxjava2:1.0.0"
implementation "com.qifan.powerpermission:powerpermission-rxjava3:1.0.0"
implementation "com.qifan.powerpermission:powerpermission-coroutines:1.0.0"
implementation "com.qifan.powerpermission:powerpermission-livedata:1.0.0"
```
### 总结
|  Package Name     |     Role      | Usage |
| ------------- |-------------|-------------|
| powerpermission            | Basic core package       | implementation "com.qifan.powerpermission:powerpermission:1.0.0" |
| powerpermission-rxjava2    | Support RxJava2          | implementation "com.qifan.powerpermission:powerpermission-rxjava2:1.0.0" |
| powerpermission-rxjava3    | Support RxJava3          | implementation "com.qifan.powerpermission:powerpermission-rxjava3:1.0.0" |
| powerpermission-coroutines | Support Kotlin Coroutine | implementation "com.qifan.powerpermission:powerpermission-coroutines:1.0.0" |
| powerpermission-livedata   | Support Android LiveData | implementation "com.qifan.powerpermission:powerpermission-livedata:1.0.0" |

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