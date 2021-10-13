mysql 缺少字段：

```shell
alter table ACT_RE_DEPLOYMENT add VERSION_ int;

alter table ACT_RE_DEPLOYMENT add column PROJECT_RELEASE_VERSION_ varchar(255);
```