$(function(){$("#submitButton").on("click",function(){var e=$.trim($("#email").val()),t=$.trim($("#emailCode").val()),a=$.trim($("#newPassword").val()),r=$.trim($("#repassword").val());return e.length<=0?($.alert("邮箱不能为空。"),!1):t.length<=0?($.alert("邮箱验证码不能为空。"),!1):a.length<=0?($.alert("新密码不能为空。"),!1):r!=a?($.alert("两次密码不一致。"),!1):($.showLoading(),void $.apiPost("emailAlterPassword",{email:e,emailCode:t,newPassword:md5(a)},function(){$.alert("密码修改成功，现在去登录。",function(){location.href="login.html"})}))});var e;$("#send").on("click",function(){var t=$(this),a=$.trim($("#email").val());if(a.length<=0)return $.alert("邮箱不能为空。"),!1;$.showLoading(),$.apiPost("sendEmailCode",{email:a},function(){t.prop("disabled",!0);var a=10;t.text($.beforeZero(a)+"秒..."),e=setInterval(function(){if(a<=1)return t.prop("disabled",!1).text("发送验证码"),void clearInterval(e);--a,t.text($.beforeZero(a)+"秒...")},1e3)})})});