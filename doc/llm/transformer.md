
## Tokenization 标记化



[https://jalammar.github.io/illustrated-transformer/](https://jalammar.github.io/illustrated-transformer/)

# Transformer
Transformer 是一种基于自注意力机制（Self-Attention）的深度学习架构，由Google团队在2017年的论文《Attention Is All You Need》中首次提出。它彻底改变了自然语言处理（NLP）领域，并成为现代大语言模型（如GPT、BERT等）的核心基础。

核心思想：是完全依赖注意力机制（无需循环或卷积结构）来捕捉输入序列中的全局依赖关系，从而实现高效的并行计算和长距离建模。


主要组成部分：


## Encoder 编码器
 ### 1.编码器-解码器（Encoder-Decoder） 结构
  编码器-解码器架构由两个主要组件组成：编码器（Encoder）和解码器（Decoder）。
  * 编码器：负责将输入序列转换为连续的表示（向量序列）。每个位置的输入都被编码为一个向量，这些向量捕捉了该位置的上下文信息。
  * 解码器：根据编码器的输出和之前的生成内容（即“已翻译”的输出），逐步生成输出序列。

#### 编码器
每个编码器又分为两个子部分：
  * 自注意力（Self-Attention ）：该层帮助编码器在编码特定单词时查看输入句子中的其他单词。
  * 前馈神经网络（Feed-Forward Neural Net）：自注意力层的输出被馈送到前馈神经网络。自注意力层的输出被馈送到前馈神经网络。


  

#### 解码器
解码器包含三层：
  * 自注意力（Self-Attention）
  * 编码器-解码器注意力（Encoder-Decoder Attention）：自注意力层的输出被馈送到前馈神经网络。
  * 前馈神经网络（Feed-Forward Neural Net）

 ### 2.自注意力机制（Self-Attention）

假设下面的句子是我们要翻译的输入句子：

“The animal didn't cross the street because it was too tired”

这句话里的“it”指的是什么？它指的是“street”还是“animal”？对人类来说，这是一个简单的问题，但对算法来说却并非如此简单。

当模型处理每个单词（输入序列中的每个位置）时，自我注意力机制允许它查看输入序列中的其他位置，以寻找有助于更好地编码该单词的线索。

自注意力机制是 Transformer 用来将对其他相关单词的“理解”融入当前正在处理的单词/向量的方法。

### 3.多头注意力机制（Multi-Head Attention）
将自注意力机制并行执行多次（多个“头”），每个头学习不同的注意力模式，最后拼接结果。

增强模型捕捉不同子空间信息的能力。

### 4.位置编码（Position Encoding）
Transformer 本身没有循环或卷积结构，无法天然感知序列顺序。

通过添加正弦/余弦位置编码（或可学习的位置嵌入）来注入位置信息。

### 5.前馈神经网络（Feed-Forward Neural Network）
每个注意力层后接一个全连接网络（通常包含两层+激活函数），对特征进行非线性变换。



### 6.残差连接与层归一化（Residual Connections & Layer Normalization）
每层输出 = 输入 + 子层输出（残差连接），缓解梯度消失。

层归一化（LayerNorm）加速训练稳定性。





















