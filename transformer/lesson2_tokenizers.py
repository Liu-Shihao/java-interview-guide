from transformers import AutoTokenizer

"""
pip install transformers
Tokenizing Text

conda activate transformer
https://github.com/HandsOnLLM/Hands-On-Large-Language-Models/blob/main/chapter02/Chapter%202%20-%20Tokens%20and%20Token%20Embeddings.ipynb
"""

sentence = "Hello World!"
tokenizer = AutoTokenizer.from_pretrained("bert-base-cased")
token_ids = tokens = tokenizer(sentence).input_ids
print(token_ids)
# [101, 8667, 1291, 106, 102]

# decode these tokens with the decode function that the tokenizer has
for token_id in token_ids:
    print(tokenizer.decode(token_id))
"""
[CLS] :CLS Token 分类标记
Hello
World
!
[SEP]： Separation Token 分隔标记，表示句子结束
"""
