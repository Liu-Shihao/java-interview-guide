
## Qwen3 Code 简介
Qwen3-Coder 是阿里云推出的专门针对代码生成与理解的 AI 模型，属于通义千问系列大模型中专注于编程领域的版本。

强大灵活的Coding Agent能力，上下文长度支持1M。


## 开通阿里云百炼
1. 登录[阿里云百炼大模型服务平台](https://bailian.console.aliyun.com/?spm=a2c4g.11186623.0.0.16fe5835dozoqh), 需要开通阿里云百炼的模型服务，有1,000,000 Token 的免费额度。
2. 创建API-Key，[API-Key](https://bailian.console.aliyun.com/?tab=model#/api-key)


# 一、Qwen Code Cli


```bash
npm install -g @qwen-code/qwen-code@latest
qwen --version
```

```bash
# Start Qwen Code
qwen
```



输入API-Key和Base URl就可以使用了
- API-Key：YOUR_DASHSCOPE_API_KEY
- BASE URl:https://dashscope.aliyuncs.com/compatible-mode/v1






# 二、Claude Code
第二种方式使用Claude Code，
首先需要安装Claude Code 客户端：

Ensure you have` Node.js version 20` or higher installed.
```bash
# Install Claude Code
npm install -g @anthropic-ai/claude-code

```

在 Claude Code 中使用百炼提供的 Qwen3-Coder-Plus 模型，只需配置以下两个环境变量：

```shell
#查看默认 Shell 类型
echo $SHELL
# 如果是 zsh 则使用 ~/.zshrc, 如果是 bash 则使用 ~/.bashrc
```

```bash
# 用您的百炼API KEY代替YOUR_DASHSCOPE_API_KEY
echo 'export ANTHROPIC_BASE_URL="https://dashscope.aliyuncs.com/api/v2/apps/claude-code-proxy"' >> ~/.zshrc
echo 'export ANTHROPIC_AUTH_TOKEN="YOUR_DASHSCOPE_API_KEY"' >> ~/.zshrc

source ~/.zshrc

echo $ANTHROPIC_BASE_URL
echo $ANTHROPIC_AUTH_TOKEN
```

---

如果是 bash 则使用 ~/.bashrc
```bash
# 如果是 bash 则使用 ~/.bashrc
echo 'export ANTHROPIC_BASE_URL="https://dashscope.aliyuncs.com/api/v2/apps/claude-code-proxy"' >> ~/.bash_profile
echo "export ANTHROPIC_AUTH_TOKEN='YOUR_DASHSCOPE_API_KEY'" >> ~/.bash_profile

source ~/.bash_profile

echo $ANTHROPIC_BASE_URL
echo $ANTHROPIC_AUTH_TOKEN
```




---
在终端中执行以下命令，运行 Claude Code。
```bash
claude
```





# Reference
- [aliyun bailian qwen3-coder document](https://bailian.console.aliyun.com/?spm=5176.29597918.J_SEsSjsNv72yRuRFS2VknO.2.338c7b08FaAFmQ&tab=api#/api/?type=model&url=https%3A%2F%2Fhelp.aliyun.com%2Fdocument_detail%2F2850166.html)
- [https://docs.anthropic.com/en/docs/claude-code/overview](https://docs.anthropic.com/en/docs/claude-code/overview)
- [https://help.aliyun.com/zh/model-studio/claude-code](https://help.aliyun.com/zh/model-studio/claude-code)
- [https://github.com/QwenLM/qwen-code](https://github.com/QwenLM/qwen-code)