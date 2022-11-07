## shell

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
# 获取当前日期   2020-07-18 14:35:06
NowTime=$(/bin/date +%Y-%m-%d' '%H:%M:%S)
echo ${NowTime}

# 返回时间 2020-08-05-10-28-29
file_time() {
    echo $(date +%Y-%m-%d-%H-%M-%S)
}

# 返回时间戳,1970 到现在
file_timestamp() {
    echo $(date +%s)
}
```

- 添加多行到文件

```bash
#!/bin/bash
# 自定义文本 END OF FILE 
echo "abcd" >a.txt

# <<EOF 定义文件的结束符
cat >a.txt <<EOF
AA111
BB222
CC333
EOF
```

- 多行赋值变量

```shell
## 添加单行或多行内容到文本

# <<EOF 定义文件的结束符
AA=$(cat <<EOF
11
22
EOF
)
# 保留换行添加双引号
echo "$AA"
json=$(
    cat <<-END
    {
    "tag_name": "11",
    "tag_name1": "11"
    }
END
)
echo $json
```

- 软连接

```shell
# 创建硬链接
ln 源文件 目标文件

# 创建软连接
ln -s 源文件 目标文件
```

- 当前目录

```shell
#!/bin/bash
set -eo pipefail
CURRENT_DIR="$(dirname "$(readlink -f "${BASH_SOURCE:-$0}")")"
echo ${CURRENT_DIR}
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

## 有趣命令

#### 判断程序是否安装

```shell
if [[ "$(command -v brew)" ]]; then
    echo "安装"
else
    echo "没散装"
fi
```
#### 使用管理员权限运行
```shell
CURRENT_USER=$(whoami)
if [[ $CURRENT_USER != "root" ]]; then
echo "请使用 sudo 运行"
exit 2
fi
```

