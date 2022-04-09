#!/usr/bin/env bash
set -eo pipefail

# 执行时间,版本文件加上这个后缀
currentTime=$(date +"%Y_%m_%d_%H_%M_%S")
backupFileSuffix="_backup_${currentTime}"

# 当 brew 不存在的时候安装
function install_brew_when_not_install() {
  if [[ "$(command -v brew)" ]]; then
    echo "brew 已经安装了"
    # 更新 brew
    brew update
  else
    echo "brew 没有安装,开始安装......"
    /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
    brew -v
  fi
}

# 安装 mac 应用程序,从 Brewfile
function install_brew_app() {
  brew tap
  brew bundle -v
}

function config_git() {
  git config --global user.name "zhangpanqin"
  git config --global user.email "zhangpanqin@outlook.com"
  config_gpg
}

function config_gpg() {
  echo "pinentry-program /usr/local/bin/pinentry-mac" >>~/.gnupg/gpg-agent.conf
  killall gpg-agent
}

# 配置 zsh
function config_zsh() {
  # 配置 zsh
  # 安装 on my zsh 用于管理 zsh 配置
  if [[ -d "$ZSH" ]]; then
    echo "已经安装了 on my zsh"
  else
    echo "安装 on my zsh"
    sh -c "$(curl -fsSL https://raw.githubusercontent.com/robbyrussell/oh-my-zsh/master/tools/install.sh)"
  fi
  # 配置 zsh
  if [[ -d "${ZSH_CUSTOM:-${ZSH:-~/.oh-my-zsh}/custom}/plugins/zsh-syntax-highlighting" ]]; then
    echo "已经安装了 zsh-syntax-highlighting"
  else
    git clone https://github.com/zsh-users/zsh-syntax-highlighting.git "${ZSH_CUSTOM:-${ZSH:-~/.oh-my-zsh}/custom}/plugins/zsh-syntax-highlighting"
  fi

  if [[ -d "${ZSH_CUSTOM:-${ZSH:-~/.oh-my-zsh}/custom}/plugins/zsh-autosuggestions" ]]; then
    echo "已经安装了 zsh-autosuggestions"
  else
    git clone https://github.com/zsh-users/zsh-autosuggestions "${ZSH_CUSTOM:-${ZSH:-~/.oh-my-zsh}/custom}/plugins/zsh-autosuggestions"
  fi

  if [[ -d "${ZSH_CUSTOM:-${ZSH:-~/.oh-my-zsh}/custom}/plugins/zsh-completions" ]]; then
    echo "已经安装了 zsh-completions"
  else
    git clone https://github.com/zsh-users/zsh-completions "${ZSH_CUSTOM:-${ZSH:-~/.oh-my-zsh}/custom}/plugins/zsh-completions"
  fi

  # 备份 .zshrc
  if [[ -f "${HOME}/.zshrc" ]]; then
    mv "${HOME}/.zshrc" "${HOME}/.zshrc${backupFileSuffix}"
  fi
  cat <<EOF >"${HOME}/.zshrc"
export PATH="/usr/local/sbin:\$PATH"
export ZSH="\$HOME/.oh-my-zsh"
ZSH_THEME="robbyrussell"
fpath+=\${ZSH_CUSTOM:-\${ZSH:-~/.oh-my-zsh}/custom}/plugins/zsh-completions/src
plugins=(git autojump zsh-syntax-highlighting zsh-autosuggestions kubectl)
source \$ZSH/oh-my-zsh.sh
eval "\$(starship init zsh)"
eval "\$(direnv hook zsh)"
EOF
}

# 安装 shell
install_brew_when_not_install
install_brew_app
config_zsh

echo "所有组件已经安装成功"
