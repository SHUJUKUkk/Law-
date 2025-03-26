# 爱律
一个基于Spring Boot的法律咨询系统，集成了用户认证、权限管理和AI辅助咨询等功能。
——_「AI赋正义，律定规则」_

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-8-orange.svg)](https://www.java.com)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-Latest-green.svg)](https://spring.io/projects/spring-security)
[![MySQL](https://img.shields.io/badge/MySQL-Latest-blue.svg)](https://www.mysql.com/)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

## 项目简介

这是一个基于Spring Boot开发的法律咨询系统，集成了用户认证、权限管理和AI辅助咨询等功能，旨在为用户提供便捷的法律咨询服务。

## 核心功能

- 🔐 **用户认证与授权**
  - 基于Spring Security的安全认证
  - 角色基础的访问控制（RBAC）
  - 管理员和普通用户权限分离

- 🤖 **AI法律助手**
  - 集成OpenAI GPT服务
  - 智能法律问答
  - 法律文书生成辅助

- 👥 **用户管理**
  - 用户注册与登录
  - 个人信息管理
  - 密码加密存储

## 技术栈

- **后端框架**: Spring Boot 2.7.3
- **安全框架**: Spring Security
- **数据库**: MySQL
- **ORM**: Spring Data JPA
- **模板引擎**: Thymeleaf
- **AI集成**: OpenAI GPT-3

## 快速开始

### 环境要求

- JDK 8+
- Maven 3.6+
- MySQL 5.7+

### 安装步骤

1. 克隆项目
```bash
git clone [项目地址]
```

2. 配置数据库
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
```

3. 构建运行
```bash
mvn clean install
mvn spring-boot:run
```

## 默认账户

- 管理员账户：admin
- 默认密码：admin123

## 许可证

本项目采用 [GNU General Public License v3.0](https://www.gnu.org/licenses/gpl-3.0.html) 开源许可证。

根据 GPLv3 许可证的规定：

- ✔️ 您可以自由地使用、修改和分发本软件
- ✔️ 您可以将本软件用于商业用途
- ❗ 如果您分发修改后的版本，您必须开源您的修改
- ❗ 您必须在您的项目中包含原始许可证和版权声明
- ❗ 您必须指明您对原始代码的修改