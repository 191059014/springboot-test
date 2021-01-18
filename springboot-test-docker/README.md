# springboot-test-docker
springboot整合maven的docker插件实现自动化打包镜像到镜像仓库，然后通过k8s运行项目
1. 执行mvn package（打包jar，生成docker镜像，并推送到镜像仓库）
