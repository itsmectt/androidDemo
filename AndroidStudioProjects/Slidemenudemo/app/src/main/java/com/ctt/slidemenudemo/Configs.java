package com.ctt.slidemenudemo;

/**
 * Created by ZYMAppOne on 2015/12/16.
 */
public class Configs {
    /*****
     * 系统相册（包含有 照相、选择本地图片）
     */
    public class SystemPicture{
        /***
         * 保存到本地的目录
         */
        public static final String SAVE_DIRECTORY = "/zym";
        /***
         * 保存到本地图片的名字
         */
        public static final String SAVE_PIC_NAME="head.jpeg";
        /***
         *标记用户点击了从照相机获取图片  即拍照
         */
        public static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
        /***
         *标记用户点击了从图库中获取图片  即从相册中取
         */
        public static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
        /***
         * 返回处理后的图片
         */
        public static final int PHOTO_REQUEST_CUT = 3;// 结果
    }
}
