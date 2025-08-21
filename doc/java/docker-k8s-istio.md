## Docker 
将应用及其依赖打包成一个轻量级的容器镜像，提供一致的运行环境。

Docker容器共享主机内核，比虚拟机更加轻量

Dockerfile常用命令
- FROM：指定基础镜像 必须为第一条指令
- RUN：在镜像构建时执行命令（如安装软件）
- COPY 将文件从主机复制到镜像中
- ADD 支持自动解压压缩包和远程 URL（但推荐优先使用 COPY）。
- WORKDIR ：设置工作目录，（后续命令的相对路径基于此目录）。
- ENV：设置环境变量（可被容器内进程访问）。
- EXPOSE：声明容器监听的端口（仅文档化，不实际暴露端口），（实际映射需通过 docker run -p 指定）。
- CMD 定义容器启动时执行的命令。提供默认命令，可被 docker run 覆盖。
- ENTRYPOINT：定义容器启动时的入口点。与 CMD 类似，但更适合需要固定执行命令的场景。可以与 CMD 结合使用，CMD 提供参数。
- ARG：在构建时传递变量（如版本号），仅在构建阶段有效。
- VOLUME：创建挂载点（数据卷），用于持久化存储或共享数据。
- USER：指定容器内运行命令的用户（默认是 root）。
- HEALTHCHECK：定义容器健康检查命令，Docker 会定期执行此命令以判断容器是否健康。
- LABEL：为镜像添加元数据（如版本、作者等），便于管理和查询。


多阶段构建：减少镜像大小，如编译阶段和生产阶段分离



## Kubernetes
Kubernetes（简称 K8s）是一个开源的 容器编排平台

- Pod 最小调度单元，包含一个或多个容器
- Deployment，管理Pod 的部署和更新
- Service，为 Pod 提供稳定的访问入口。
  - ClusterIP，集群内部访问。
  - NodePort，通过节点端口暴露服务
- Configmap:存储非敏感配置（如环境变量、配置文件）。
- Secret :存储敏感数据（如密码、密钥），以 Base64 编码存储（非加密）。
- Volume 卷：用于为 Pod 中的容器提供 持久化存储 或 共享存储 的机制。它解决了容器文件系统的临时性问题（容器重启后数据丢失），允许数据在容器生命周期之外持久存在，并支持多个容器之间共享数据。
- PersistentVolume (PV)：集群级别的存储资源（如云磁盘、NFS）。
- PersistentVolumeClaim (PVC)：用户对存储资源的请求（类似“申请存储”）。
- Namespace：逻辑隔离集群资源，用于多团队或多环境（如 dev/test/prod）。
- Ingress:管理外部访问（HTTP/HTTPS 路由），替代 NodePort/LoadBalancer
- ServiceAccount：Kubernetes 中的用户身份，通过绑定 Role 或 ClusterRole，限制 Pod 能访问哪些 API 资源。

### ClusterIP和NodePort的区别

ClusterIP 访问范围：仅在 Kubernetes 集群内部可访问（其他 Pod 或 Service）。由 Kubernetes 自动分配一个虚拟 IP（VIP），仅在集群内有效。用于集群内服务间的通信（如微服务之间的调用）。

NodePort 访问范围：允许从集群外部访问（通过节点的 IP 和端口）。


### 如果服务无法访问可能有哪些原因？

1. 检查Service类型，如果使用了ClusterIP类型，默认类型，仅集群内可访问。
2. 检查Pod状态和健康，通过探针判断pod是否健康
3. 确保节点的安全组策略允许外部访问NodoPort范围（3000 ~ 32767）
4. 检查Ingress配置是否正确，是否有正确的路由规则。
5. 集群组件故障，排查kube-proxy、CNI插件、DNS

## Istio
Istio 是一个开源的 服务网格（Service Mesh） 框架，用于管理微服务之间的通信、安全、流量控制和可观测性。
它通过 Sidecar 代理（基于 Envoy）透明地注入到每个 Pod 中，提供统一的流量管理、策略执行和监控能力，而无需修改应用代码。

核心功能：
- 流量管理：动态路由，AB测试，金丝雀发布，负载均衡
- 安全：服务间mTLS加密，
- 可观测性：分布式追踪 Jaeger，指标监控 Prometheus，日志集成 Kiali

分为数据平面 Data Plane 和控制平面 Control Plane

Envoy Proxy （Sidecar） ：每个Pod中注入代理，统一负责流量分发，策略执行和指标表收集
Virtual Service 虚拟服务：定义流量路由规则
Destination Rule 目标规则：定义服务子集（Subset）和负载均衡策略
IstioGateway：管理入站流量（类似 Kubernetes Ingress，但更灵活）。
ServiceEntry：将外部服务纳入网格（如访问第三方 API）。


Kiali：服务拓扑和健康状态。
Jaeger：分布式追踪。
Prometheus + Grafana：指标监控。







