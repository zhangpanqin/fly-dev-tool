## AWS

```shell
aws sts assume-role --role-arn arn:aws:iam::{account id}:role/{roleName}--role-session-name s3-access-example

unset AWS_ACCESS_KEY_ID AWS_SECRET_ACCESS_KEY AWS_SESSION_TOKEN
aws sts get-caller-identity
```

## Item2 和 zsh 配置

```shell
# 安装 homebrew
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
# 安装 iterm2
brew install --cask iterm2
# 安装 zsh
brew install zsh
# 安装 on my zsh 用于管理 zsh 配置
sh -c "$(curl -fsSL https://raw.githubusercontent.com/robbyrussell/oh-my-zsh/master/tools/install.sh)"
# 设置 default shell 为 zsh
chsh -s $(which zsh)
# 设置 zsh 主题
brew tap homebrew/cask-fonts
brew install --cask font-hack-nerd-font
brew install starship
echo 'eval "$(starship init zsh)"' >> ~/.zshrc
```

### 配置 zsh 及 zsh 插件

```shell
# 查看目录列表，tree -L 1 , -L 指定最大深度
brew install tree
brew install fzf
# 目录环境变量添加 .envrc export GIT_SSH_COMMAND="ssh -i ~/.ssh/id_rsa_rba"
brew install direnv
eval "$(direnv hook zsh)"
# 配置 zsh 插件
# j 智能进入目录
brew install autojump
git clone https://github.com/zsh-users/zsh-syntax-highlighting.git ${ZSH_CUSTOM:-~/.oh-my-zsh/custom}/plugins/zsh-syntax-highlighting
git clone https://github.com/zsh-users/zsh-autosuggestions ${ZSH_CUSTOM:-~/.oh-my-zsh/custom}/plugins/zsh-autosuggestions
git clone https://github.com/zsh-users/zsh-completions ${ZSH_CUSTOM:-${ZSH:-~/.oh-my-zsh}/custom}/plugins/zsh-completions

# 插入以下内容到 ~/.zshrc
# fpath+=${ZSH_CUSTOM:-${ZSH:-~/.oh-my-zsh}/custom}/plugins/zsh-completions/src
# plugins=(git autojump zsh-syntax-highlighting zsh-autosuggestions kubectl)
# source $ZSH/oh-my-zsh.sh
```

### AWS 和 EKS

```shell
# aws cli
brew install awscli
brew install aws-iam-authenticator
brew install kubectl
brew install terraform

# 在命令行打开文件，配合 kubectx 和 kubens
brew install fzf 
# 方便切换 k8s 上下文和 namespace
brew install kubectx 
```





## 参考资料

[macOS Setup Guide](https://sourabhbajaj.com/mac-setup)
