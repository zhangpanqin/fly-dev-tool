### 配置 gpg
```shell
# 不需要在 ~/.zshrc 配置 export GPG_TTY=$(tty)
brew install pinentry-mac
echo "pinentry-program /usr/local/bin/pinentry-mac" >> ~/.gnupg/gpg-agent.conf
killall gpg-agent
```