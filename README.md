# CameraSurfaceView-master
<br>基于surfaceView在子线程运行的相机控件，支持横竖屏切换
<br>

接入
----
<br>1.下载后在根目录下的build.gradle添加依赖：
```
dependencies {
    implementation project(':camerasurfaceview')
}
```
使用
----
<br>1.在xml文件
```
<com.marvin.camerasurfaceview.CameraSurfaceView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/sf_camera"
    app:cameraWidth = "1920"   //可以不写
    app:cameraHeight = "1080"  //可以不写 
    app:cameraID = "1"
    />
```
一般手机的相机有很多分辨率的组合，可以根据屏幕比例选择对应的cameraWidth
 <br>和cameraHeight，如果该相机不存在这样的分辨率，则自动选择最高且比例最接近
 <br>的分辨率。
 <br>cameraID为"0"是后置摄像头，cameraID为"1"是前置摄像头
```
    protected void onStart() {
        super.onStart();
        sf_camera.onStart();
    }

    protected void onPause() {
        super.onPause();
        sf_camera.onPause();
    }

    protected void onDestroy() {
        super.onDestroy();
        sf_camera.onDestroy();
    }
```
```
     CameraSurfaceView.SetOnPreviewFrameListener(new PreviewFrameListener() {
            @Override
            public void PreviewFrame(byte[] bytes, Camera camera, int width, int height) {
                //获取视频流并处理
            }
     });
```
License
----
```
Copyright 2017 hm-Marvin

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
```
 
