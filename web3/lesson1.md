# 密码学
cypto-cyrrency
cryptographic hash function
挖矿很难，验证很容易
SHA256  Secure Hash Algorithm

哈希， 签名



签名管理

比特币去中心化，没有银行机构
每个用户自己可以创建账户，即创建公钥和私钥


对方使用接受者的公钥加密，自己使用私钥解密

公钥加密（需要把公钥暴漏给对方），
私钥解密，相当于账户密码 

发起交易：
使用私钥签名，其他人使用公钥验证签名

## 数据结构

hash pointers  哈希指针

Merkle Tree 默克尔树
Binary Merkle Tree  二叉默克尔树

block header  区块头
    version 版本号
    prev_block_hash  上一个区块的哈希值
    merkle_root 默克尔树的根哈希值
    timestamp  时间戳
    bits  目标值
    nonce  随机数

block body  区块体
transaction list 交易列表

Merkle proof  
full node 全节点
light node 轻节点 只保存block header信息，无法验证交易

