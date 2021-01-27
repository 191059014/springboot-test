# springboot-test-docker
springboot整合maven的docker插件实现自动化打包镜像到镜像仓库，然后通过k8s运行项目，可利用jenkins配置项目并发版，jenkins配置有两种：
1. \src\main\resources\image\jenkins_config_1.png（不利用deploy.sh脚本）
关键脚本：
```bash
cd /data/pmarketing ; 
helm del --purge springboottestdocker-dev ; 
helm install -n springboottestdocker-dev ./springboottestdocker/ --values ./springboottestdocker/values.yaml --namespace gyjx-pmarketing --set image.tag=$imageVersion  --set probe.enabled=false
```
2. \src\main\resources\image\jenkins_config_2.png（利用deploy.sh脚本）
关键脚本：
```bash
cd /data/jenkins/pmarketing/pmbizweb/
rm -rf deploy
tar -zxvf deploy.tar.gz
cd deploy
sh deploy.sh pmbizweb pmbizweb-dev
```