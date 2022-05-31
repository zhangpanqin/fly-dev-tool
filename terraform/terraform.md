## 设置 Terraform token 用于访问

```shell
# 保存登录凭证到 ${HOME}/.terraform.d/credentials.tfrc.json，这个是全局配置
terraform login

# 推荐配置，也是全局，配置了 `export TF_CLI_CONFIG_FILE=.terraformrc` 就不会找全局
${HOME}/.terraformrc


mkdir -p $HOME/.terraform.d/plugin-cache && cat ${HOME}/.terraformrc <<EOF
# This directory must already exist before Terraform will cache plugins; Terraform will not create the directory itself.
plugin_cache_dir = "$HOME/.terraform.d/plugin-cache"
credentials "app.terraform.io" {
token = ""
}
<<EOF
```

