package com.study.jpkc.common.constant;

/**
 * @author isharlan.hu@gmail.com
 * @date 2021/2/10 0:25
 * @desc 邮件常量类
 */
public final class MailConstant {

    private MailConstant() {
        throw new IllegalStateException("Utility class");
    }

    public static final String REGISTER_MAIL_TEMPLATE = "<!DOCTYPE html>\n" +
            "<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n" +
            "    <head>\n" +
            "        <meta charset=\"UTF-8\">\n" +
            "        <title>激活邮件</title>\n" +
            "        <style type=\"text/css\">\n" +
            "            * {\n" +
            "                margin: 0;\n" +
            "                padding: 0;\n" +
            "                box-sizing: border-box;\n" +
            "                font-family: Arial, Helvetica, sans-serif;\n" +
            "               }\n" +
            "            body {\n" +
            "                background-color: #ECECEC;\n" +
            "                 }\n" +
            "            .container {\n" +
            "                width: 800px;\n" +
            "                margin: 50px auto;\n" +
            "             }\n" +
            "            .header {\n" +
            "                height: 80px;\n" +
            "                background-color: #49bcff;\n" +
            "                border-top-left-radius: 5px;\n" +
            "                border-top-right-radius: 5px;\n" +
            "                padding-left: 30px;\n" +
            "             }\n" +
            "            .header h2 {\n" +
            "                padding-top: 25px;\n" +
            "                color: white;\n" +
            "                       }\n" +
            "            .content {\n" +
            "                background-color: #fff;\n" +
            "                padding-left: 30px;\n" +
            "                padding-bottom: 30px;\n" +
            "                border-bottom: 1px solid #ccc;\n" +
            "                }\n" +
            "            .content h2 {\n" +
            "                padding-top: 20px;\n" +
            "                padding-bottom: 20px;\n" +
            "                }\n" +
            "            .content p {\n" +
            "                padding-top: 10px;\n" +
            "                       }\n" +
            "            .footer {\n" +
            "                background-color: #fff;\n" +
            "                border-bottom-left-radius: 5px;\n" +
            "                border-bottom-right-radius: 5px;\n" +
            "                padding: 35px;\n" +
            "            }\n" +
            "            .footer p {\n" +
            "                color: #747474;\n" +
            "                padding-top: 10px;\n" +
            "            }\n" +
            "        </style>\n" +
            "    </head>\n" +
            "    <body>\n" +
            "        <div class=\"container\">\n" +
            "            <div class=\"header\">\n" +
            "                <h2>欢迎加入精品课程网</h2>\n" +
            "        </div>\n" +
            "            <div class=\"content\">\n" +
            "                <h2>亲爱的用户您好</h2>\n" +
            "                <p>您的邮箱：<b>" +
            "                   <span>${userEmail}</span>" +
            "                   </b>" +
            "               </p>\n" +
            "                <p>您的激活链接：<b><a href='${activateUrl}'>点击激活</a></b></p>\n" +
            "                <p>您注册时的日期：<b><span>${userCreated}</span></b></p>\n" +
            "                <p>当您在使用本网站时，务必要遵守法律法规</p>\n" +
            "                <p>如果您有什么疑问可以联系管理员，Email: <b>isharlan.hu@gmail.com</b></p>\n" +
            "            </div>\n" +
            "            <div class=\"footer\">\n" +
            "                <p>此为系统邮件，请勿回复</p>\n" +
            "                <p>请保管好您的信息，避免被他人盗用</p>\n" +
            "                <p>©Jpkc</p>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>";

    public static final String MAIL_VERIFY_TEMPLATE = "<head>\n" +
            "    <base target=\"_blank\" />\n" +
            "    <style type=\"text/css\">::-webkit-scrollbar{ display: none; }</style>\n" +
            "    <style id=\"cloudAttachStyle\" type=\"text/css\">#divNeteaseBigAttach, #divNeteaseBigAttach_bak{display:none;}</style>\n" +
            "    <style id=\"blockquoteStyle\" type=\"text/css\">blockquote{display:none;}</style>\n" +
            "    <style type=\"text/css\">\n" +
            "        body{font-size:14px;font-family:arial,verdana,sans-serif;line-height:1.666;padding:0;margin:0;overflow:auto;white-space:normal;word-wrap:break-word;min-height:100px}\n" +
            "        td, input, button, select, body{font-family:Helvetica, 'Microsoft Yahei', verdana}\n" +
            "        pre {white-space:pre-wrap;white-space:-moz-pre-wrap;white-space:-pre-wrap;white-space:-o-pre-wrap;word-wrap:break-word;width:95%}\n" +
            "        th,td{font-family:arial,verdana,sans-serif;line-height:1.666}\n" +
            "        img{ border:0}\n" +
            "        header,footer,section,aside,article,nav,hgroup,figure,figcaption{display:block}\n" +
            "        blockquote{margin-right:0px}\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body tabindex=\"0\" role=\"listitem\">\n" +
            "<table width=\"700\" border=\"0\" align=\"center\" cellspacing=\"0\" style=\"width:700px;\">\n" +
            "    <tbody>\n" +
            "    <tr>\n" +
            "        <td>\n" +
            "            <div style=\"width:700px;margin:0 auto;border-bottom:1px solid #ccc;margin-bottom:30px;\">\n" +
            "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"700\" height=\"39\" style=\"font:12px Tahoma, Arial, 宋体;\">\n" +
            "                    <tbody><tr><td width=\"210\"></td></tr></tbody>\n" +
            "                </table>\n" +
            "            </div>\n" +
            "            <div style=\"width:680px;padding:0 10px;margin:0 auto;\">\n" +
            "                <div style=\"line-height:1.5;font-size:14px;margin-bottom:25px;color:#4d4d4d;\">\n" +
            "                    <strong style=\"display:block;margin-bottom:15px;\">尊敬的用户：<span style=\"color:#f60;font-size: 16px;\"></span>您好！</strong>\n" +
            "                    <strong style=\"display:block;margin-bottom:15px;\">\n" +
            "                        您正在进行<span style=\"color: red\">邮箱地址更改</span>操作，请在验证码输入框中输入：<span style=\"color:#f60;font-size: 24px\">${verifyCode}</span>，以完成操作。\n" +
            "                    </strong>\n" +
            "                </div>\n" +
            "                <div style=\"margin-bottom:30px;\">\n" +
            "                    <small style=\"display:block;margin-bottom:20px;font-size:12px;\">\n" +
            "                        <p style=\"color:#747474;\">\n" +
            "                            注意：此操作可能会修改您的密码、登录邮箱或绑定手机。如非本人操作，请及时登录并修改密码以保证帐户安全\n" +
            "                            <br>（工作人员不会向你索取此验证码，请勿泄漏！)\n" +
            "                        </p>\n" +
            "                    </small>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "            <div style=\"width:700px;margin:0 auto;\">\n" +
            "                <div style=\"padding:10px 10px 0;border-top:1px solid #ccc;color:#747474;margin-bottom:20px;line-height:1.3em;font-size:12px;\">\n" +
            "                    <p>此为系统邮件，请勿回复<br>\n" +
            "                        请保管好您的邮箱，避免账号被他人盗用\n" +
            "                    </p>\n" +
            "                    <p>精品课程网</p>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </td>\n" +
            "    </tr>\n" +
            "    </tbody>\n" +
            "</table>\n" +
            "</body>";
}
