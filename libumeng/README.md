
https://developer.umeng.com/docs/66632/detail/66639#h3--

1. 自己去友盟，微信，qq，微博等平台注册账号，拿到各种key，appId

   > 注意： 注册账号的时候，包名（applicationId）和签名信息一定不要搞错了。签名信息一定要用你自己准备用来打包的那个签名文件里面的签名信息(MD5)

2. 在 libraray 下面新建一个 和你applicationId 一样的包路径文件夹，然后把 wxapi 这个包移动过去

3.  AndroidManifest.xm修改：

   1. 在 libaray 的 AndroidManifest.xml 文件中 确认 WXEntryActivity 这activity 的包路径和你的 applicationId 一致
   2. 把你自己申请的QQ 平台的 `<data android:scheme="tencent1109759123" />`   App Id 替换掉，不要把 tencent 删掉
   3. 如果需要增加新的分享或者第三方登录的平台，只需要按照集成文档粘贴过去即可

4. 在ShareUtils.java 这个类中 把自己的ID 替换掉即可

5. 对于QQ ,微博等平台，需要在你 分享或者登录的 Activity 中重写 ：

   ```java

       @Override
       protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
           super.onActivityResult(requestCode, resultCode, data);

           UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
       }
   ```

   ​

