#!/usr/bin/env bash
set -eo pipefail

# 使用管理员权限运行
CURRENT_USER=$(whoami)
if [[ $CURRENT_USER != "root" ]]; then
    echo "请使用 sudo 运行"
    exit 2
fi

# 执行时间,版本文件加上这个后缀
currentTime=$(date +"%Y_%m_%d_%H_%M_%S")
backupFileSuffix="_backup_${currentTime}"

# 当 brew 不存在的时候安装
function install_brew_when_not_install() {
  if [[ "$(command -v brew)" ]]; then
    echo "brew 已经安装了"
  else
    echo "brew 没有安装,开始安装......"
    /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
    brew -v
  fi
}

# 安装 mac 应用程序,从 Brewfile
function install_brew_app() {
  brew tag
  brew bundle -v
}

# 配置 zsh
function config_zsh() {
  # 配置 zsh
  # 安装 on my zsh 用于管理 zsh 配置
  sh -c "$(curl -fsSL https://raw.githubusercontent.com/robbyrussell/oh-my-zsh/master/tools/install.sh)"
  # 设置 default shell 为 zsh
  chsh -s $(which zsh)
  # 配置 zsh
  git clone https://github.com/zsh-users/zsh-syntax-highlighting.git "${ZSH_CUSTOM:-~/.oh-my-zsh/custom}"/plugins/zsh-syntax-highlighting
  git clone https://github.com/zsh-users/zsh-autosuggestions "${ZSH_CUSTOM:-~/.oh-my-zsh/custom}"/plugins/zsh-autosuggestions
  git clone https://github.com/zsh-users/zsh-completions "${ZSH_CUSTOM:-${ZSH:-~/.oh-my-zsh}/custom}"/plugins/zsh-completions

  # 备份 .zshrc
  if [[ -f "${HOME}/.zshrc" ]]; then
    mv "${HOME}/.zshrc" "${HOME}/.zshrc${backupFileSuffix}"
  fi
  cat <<EOF >"${HOME}/.zshrc"
export ZSH="\$HOME/.oh-my-zsh"
ZSH_THEME="robbyrussell"
eval "\$(starship init zsh)"
eval "\$(direnv hook zsh)"
fpath+=\${ZSH_CUSTOM:-\${ZSH:-~/.oh-my-zsh}/custom}/plugins/zsh-completions/src
plugins=(git autojump zsh-syntax-highlighting zsh-autosuggestions kubectl)
source \$ZSH/oh-my-zsh.sh
eval "\$(starship init zsh)"
eval "\$(direnv hook zsh)"
export GPG_TTY=\$(tty)
EOF
}

# 安装 shell
install_brew_when_not_install
install_brew_app
config_zsh
