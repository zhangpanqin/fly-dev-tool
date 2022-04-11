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