[
  {
    "path": "add",
    "request": ${model},
    "response": {
      "data": {
        "id": "1231312"
      },
      "errcode": 200,
      "errmsg": "ok"
    }
  },
  {
    "path": "get",
    "request": {
      "id": "1231312"
    },
    "response": {
      "data": ${model},
      "errcode": 200,
      "errmsg": "ok"
    }
  },
  {
    "path": "query",
    "request": {
      "page": 1,
      "pageSize": 10,
      "params": {
        "sort": "排序",
        "where": "rsql"
      }
    },
    "response": {
      "data": [${model}],
      "errcode": 200,
      "errmsg": "ok",
      "page": {
        "page": 1,
        "pageSize": 10,
        "total": 100
      }
    }
  }
]