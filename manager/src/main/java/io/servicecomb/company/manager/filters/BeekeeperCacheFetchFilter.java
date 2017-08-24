/*
 * Copyright 2017 Huawei Technologies Co., Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.servicecomb.company.manager.filters;

import static io.servicecomb.company.manager.filters.FilterConstants.ANCESTORS_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import io.servicecomb.company.manager.archive.ProjectArchive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("archive")
class BeekeeperCacheFetchFilter extends CacheFetchFilter {

  @Autowired
  BeekeeperCacheFetchFilter(ProjectArchive<String, String> archive, PathExtractor pathExtractor) {
    super(archive, pathExtractor);
  }

  @Override
  protected String requestDescription() {
    return "count bee ancestors";
  }

  @Override
  protected String responseContentType() {
    return APPLICATION_JSON_UTF8_VALUE;
  }

  @Override
  protected String pathInRequest() {
    return ANCESTORS_PATH;
  }

  @Override
  public int filterOrder() {
    return 3;
  }
}
