#!/bin/bash

project_name=$1
helm_name=$project_name
images_version=`awk '/<version>[^<]+<\/version>/{gsub(/<version>|<\/version>/,"",$1);print $1;exit;}' ../pom.xml`


if [[ $2 != "" ]]
then
  helm_name=$2
fi

helm_command=`helm get ${helm_name}`
helm_ret=`echo $helm_command`

#echo "res: "${helm_ret}


if [[ $helm_ret != "" ]]
then
  helm upgrade ${helm_name} ./${project_name} --set image.tag=$images_version
else
  helm install -n ${helm_name} ./${project_name} --namespace gyjx-pmarketing --set image.tag=$images_version
fi

