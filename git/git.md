### 配置 gpg
```shell
# 不需要在 ~/.zshrc 配置 export GPG_TTY=$(tty)
brew install pinentry-mac
echo "pinentry-program /usr/local/bin/pinentry-mac" >> ~/.gnupg/gpg-agent.conf
killall gpg-agent
```
### 配置某个 git 仓库士通特定的 private secret
```shell
git config --local user.name xx
git config --local user.email xxx
git config --local commit.gpgSign true
git config --local user.signingkey D7157434BC92C13C
git config --local core.sshCommand "ssh -o IdentitiesOnly=yes -i ~/.ssh/id_rsa_rba"
```

### 配置某个目录使用那个 git 配置
- ~/.gitconfig
```git
[user]
name = zhangpanqin
email = zhangpanqin@outlook.com
[core]
autocrlf = input
[includeIf "gitdir:/Users/panqinzhang/xx/"]
path = /Users/panqinzhang/xx/.gitconfig
```
- /Users/panqinzhang/xx/.gitconfig
```git
[core]
sshCommand = ssh -i ~/.ssh/id_rsa_xx
[commit]
gpgSign = true
[user]
signingkey = D7157434BC92C13C
name = xx
email = xx
```

### git submodule
可以在仓库下关联另一个仓库
```shell
# 生成 .shared folder 或者 clone 代码
git submodule update --init

# 第一次初始化添加 .shared 关联那个仓库地址
git submodule add <git repo url> .shared

# 在根目录更新 .shared 代码
git submodule update --remote --rebase
```