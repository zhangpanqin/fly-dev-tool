## shell 插件

- jq，操作 json 文件

```shell
yum install epel-release -y
yum update -y
yum install jq -y
```

- 年月日

```shell
# 2022-04-03-13-32-02
date +"%Y-%m-%d-%H-%M-%S"
```

## shell 模板

```shell
#!/bin/bash
set -eo pipefail

# 或者 bash -eo pipefail script.sh
```



## 远程执行命令

```shell
# 双引号，必须有。如果不加双引号，第二个ls命令在本地执行
# 分号，两个命令之间用分号隔开
ssh local_centos7 'cd $HOME ; echo "111">aaa.txt'
```

多行复杂命令

```shell
#!/bin/bash
set -eo pipefail
ssh local_centos7 >/dev/null 2>&1 <<EOF
cd \$HOME
touch abcdefg1.txt
exit
EOF
echo done!
```

下载远程命令执行

```shell
ssh local_centos7 "curl -fsSL -O https://raw.githubusercontent.com/zhangpanqin/fly-circleci/HEAD/not_set_eo_pipefail.sh ; /bin/bash ./not_set_eo_pipefail.sh ; rm ./not_set_eo_pipefail.sh"
```



## 基础命令

```shell
# 递归创建命令
mkdir -p /home/panqin/aa/bb
```

