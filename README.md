# ServiceComb Demo - Company [![Build Status](https://travis-ci.org/ServiceComb/ServiceComb-Company-WorkShop.svg?branch=master)](https://travis-ci.org/ServiceComb/ServiceComb-Company-WorkShop)[![Coverage Status](https://coveralls.io/repos/github/ServiceComb/ServiceComb-Company-WorkShop/badge.svg)](https://coveralls.io/github/ServiceComb/ServiceComb-Company-WorkShop)

## Purpose
In order for users to better understand how to develop microservices using ServiceComb, an easy to
understand demo is provided.

## Architecture of Company
* Manager (API gateway) 
* Doorman (authentication service)
* Worker (computing service)
* Beekeeper (computing service)
* Bulletin board (service registry)
* Project archive (request cache)
* Human resource (service governance)

Please read the [blog post](http://servicecomb.io/docs/linuxcon-workshop-demo/) on the detailed explanation of this project.

## Prerequisites
You will need:
1. [Oracle JDK 1.8+][jdk]
2. [Maven 3.x][maven]
3. [Docker][docker]
4. [Docker compose(optional)][docker_compose]
5. [Docker machine(optional)][docker_machine]
6. [curl][curl]
7. [MySQL][mysql]

[jdk]: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
[maven]: https://maven.apache.org/install.html
[docker]: https://www.docker.com/get-docker
[docker_compose]: https://docs.docker.com/compose/install/
[docker_machine]: https://docs.docker.com/machine/install-machine/
[curl]: https://curl.haxx.se
[mysql]: https://dev.mysql.com/downloads/

## Run Services
A `docker-compose.yaml` file is provided to start all services and their dependencies as docker containers.
1. Build all service images using command `mvn package -Pdocker`
2. Run all service images using command `docker-compose up`

If you are using [Docker Toolbox](https://www.docker.com/products/docker-toolbox), please add an extra profile `-Pdocker-machine`.

```mvn package -Pdocker -Pdocker-machine```

## Run Integration Tests

```
mvn verify -Pdocker -Pdocker-machine
```

## Verify services
You can verify the services using curl by the following steps:
1. Retrieve manager's ip address
  * If you use docker compose:
    ```bash
    export HOST="127.0.0.1:8083"
    ```
  * If you use docker machine(supposed your docker machine name is `default`):
    ```bash
    export HOST=$(docker-machine ip default):8083
    ```
2. Log in and retrieve token from `Authorization` section
    ```bash
    curl -v -H "Content-Type: application/x-www-form-urlencoded" -d "username=jordan&password=password" -XPOST "http://$HOST/doorman/rest/login"
    ```  
    Then you can copy the token from the `Authorization` section and use it to replace the `Authorization` header in the following requests.
3. Get the sixth fibonacci number from the worker service
    ```bash
    curl -H "Authorization: replace_with_the_authorization_token" -XGET "http://$HOST/worker/fibonacci/term?n=6"
    ```
4. Get the number of drone's ancestors at the 30th generation from the beekeeper service
    ```bash
    curl -H "Authorization: replace_with_the_authorization_token" -XGET "http://$HOST/beekeeper/rest/drone/ancestors/30"
    ```
5. Get the number of queen's ancestors at the 30th generation from the beekeeper service
    ```bash
    curl -H "Authorization: replace_with_the_authorization_token" -XGET "http://$HOST/beekeeper/rest/queen/ancestors/30"
    ```

## Auto deploy on [Huawei Cloud][huawei_cloud]
To auto compile, build, deploy and run this workshop demo on Huawei Cloud's [Service Stage Platform][service_stage], you need the following steps:

1. A registered [Service Stage][service_stage] account.
2. Auto build and publish your docker image to Huawei's Image Warehouse, details can refer to [auto publish guide][publish_guide].
3. Auto deploy using Huawei Cloud's orchestration feature, details can refer to [orchestration guide][orchestration_guide]. 

[huawei_cloud]: http://www.hwclouds.com
[publish_guide]: docs/how-to-auto-publish-images-to-huawei-cloud.md
[orchestration_guide]: docs/how-to-auto-deploy-on-huawei-cloud.md

## Auto deploy on kubernetes cluster      
To auto pull images from servicecomb in docker hub, run on kubernetes cluster whether on gce or bare-metal.      
Reference to [Run Company on Kubernetes Cluster](kubernetes/README.md)

## 在华为云上自动部署

本章节介绍基于华为微服务云应用平台[Service Stage ][service_stage]，实现自动编译、构建、部署和运行的步骤。

1. 一个已注册的[Service Stage][service_stage]帐号。
2. 自动编译、构建和发布Docker镜像到华为的镜像仓库，详情可见[自动发布指南][publish_guide_cn] 。
3. 使用华为云的编排功能自动部署微服务，详情可见[自动部署指南][orchestration_guide_cn] 。

[service_stage]: https://servicestage.hwclouds.com/servicestage
[publish_guide_cn]: docs/how-to-auto-publish-images-to-huawei-cloud-cn.md
[orchestration_guide_cn]: docs/how-to-auto-deploy-on-huawei-cloud-cn.md
