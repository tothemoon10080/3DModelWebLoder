# 3DModelWebLoder
Base on java , javascript ,Spring boot and mybatis.
This this my first java program, and it is not finished yet.

Use Three.js to lode 3DModel on web.

Have a Login page and use cookies to check and remember user.

Allowed user to submit .glb .gltf or .mmd and .zip file and unzip automatically.

#to setup 
Mysql run inst.sql 

have to set up ngnix to Map your disks to network ports. 


need to change furl(ROOT DIR OF YOUE NGINX URL SUCH AS http://127.0.0.1:9018/) and fpath(ROOT DIR OF NGINX PATH SUCH AS D:/model/) on src\main\resources\static\bundle\Messages.properties & 
file_http_url, file_save_path, username and password of Mysql on src\main\resources\application.yml
