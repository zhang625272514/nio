/**
 * Created by cheng on 2015/8/23.
 */
package com.study.base.nio.server;

// -------- MyServer2
// ex: http://blog.csdn.net/kongxx/article/details/7259837
// 1. keytool -genkey -alias mysocket -keyalg RSA -keystore mysocket.jks , 在提示输入项中，密码项自己给定，其它都不改直接回车
// 2. 运行Server， java -Djavax.net.ssl.keyStore=mysocket.jks -Djavax.net.ssl.keyStorePassword=mysocket com.study.base.nio.server.MyServer2
// 3.运行Client, java -Djavax.net.ssl.trustStore=mysocket.jks  -Djavax.net.ssl.trustStorePassword=mysocket com.study.base.nio.client.MyClient2