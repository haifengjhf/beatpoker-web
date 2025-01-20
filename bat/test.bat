ALTER USER 'root'@'localhost' IDENTIFIED BY '_BeatPoker123456789';
mysql -uroot -p_BeatPoker123456789
source /Users/jinhaifeng/personal/code_project/beatpoker-web/bat/mysql.sql;

sftp root@129.226.155.48
psw:Jhf123456789

./mvnw clean package
./mvnw clean package -DskipTest //跳过测试，加快构建

nohup java -jar ./beatpoker-web-0.0.1.jar  >nohup 2>&1 &

netstat -ap | grep -i 8531

https://localhost:8531/masterUser/register?emailAddress=12260723@qq.com&nickName=nickName
https://localhost:8531/masterUser/login?emailAddress=12260723@qq.com&password=123456
https://localhost:8531/masterUser/loginWithToken?userId=5d757cd0267134f0c7c72be7b0dee279&token=417a5673f3d5b0e49e6187d434d5b672
https://localhost:8531/masterUser/refreshToken?userId=5d757cd0267134f0c7c72be7b0dee279&token=417a5673f3d5b0e49e6187d434d5b672


https://localhost:8531/masterUser/register?emailAddress=jinhaifeng@cmhi.chinamobile.com&password=406843f4af272ae5089e42e3ccafc0f1&nickName=jinhaifeng
https://localhost:8531/masterUser/login?emailAddress=jinhaifeng@cmhi.chinamobile.com&password=406843f4af272ae5089e42e3ccafc0f1
https://localhost:8531/masterUser/changePassword?userId=780387ca32ac0672b3492b88d2b7a1da&token=8fd0d7d99891ea26d1d82c90916e8cf7&newPassword=d0edc76f1371af5df94afd6547833fe8
https://localhost:8531/masterUser/resetPassword?emailAddress=jinhaifeng@cmhi.chinamobile.com
https://localhost:8531/masterUser/confirmResetPassword?emailAddress=jinhaifeng@cmhi.chinamobile.com&verificationCode=788159
https://localhost:8531/masterUser/login?emailAddress=jinhaifeng@cmhi.chinamobile.com&password=ncWZ9F

new password is :zefjBf
https://localhost:8531/masterUser/login?emailAddress=jinhaifeng@cmhi.chinamobile.com&password=67efedb0d5d525f1a0781bdf5651d13e

https://localhost:8531/masterUser/changeNickName?userId=780387ca32ac0672b3492b88d2b7a1da&token=0ec6ebb4ace065699354b84244733799&newNickName=newNickName

https://localhost:8531/userProfit/updateProfit?userId=780387ca32ac0672b3492b88d2b7a1da&token=0ec6ebb4ace065699354b84244733799&diffScore=10
https://localhost:8531/userProfit/getTopProfit?topN=100&curUserId=780387ca32ac0672b3492b88d2b7a1da



//release test
https://jhf.icu:8531/userProfit/getTopProfit?topN=100&curUserId=780387ca32ac0672b3492b88d2b7a1da