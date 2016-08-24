# simplify
a android library to make common function easy

使用方法
-----
通用ListView adapter
```java
SimpleArrayAdapter adapter = new SimpleArrayAdapter.Builder(this)
                .itemLayoutId(R.layout.list_item)
                .from("name", "age")
                .to(R.id.name, R.id.age, R.id.button)
                .setListData(list)
                .setmChildViewClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.button:
                                User user = (User) v.getTag();
                                Toast.makeText(ListActivity.this, "Button click " + user.name, Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                })
                .setmViewBinder(new SimpleAdapter.ViewBinder() {
                    @Override
                    public boolean setViewValue(View view, Object data, String textRepresentation) {
                        return false;
                    }
                })
                .build();
```

设置  
-----
将下载的jar包放到工程中，或者在build.gradle中配置依赖
```groovy
dependencies {      
	compile 'com.github.timesfire:simplify:0.1.1'
}
```

License
-------

    Copyright 2013 readyState Software Limited

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

 [1]: http://repository.sonatype.org/service/local/artifact/maven/redirect?r=central-proxy&g=com.readystatesoftware.systembartint&a=systembartint&v=LATEST&&c=jar