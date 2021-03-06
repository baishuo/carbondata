/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.carbondata.core.indexstore;

import java.io.Serializable;
import java.net.URI;
import java.util.List;
import java.util.Objects;

import org.apache.carbondata.core.datastore.impl.FileFactory;

import org.apache.hadoop.fs.Path;

/**
 * Holds partition information.
 */
public class PartitionSpec implements Serializable {

  private static final long serialVersionUID = 4828007433384867678L;

  /**
   * It holds the partition information in columnName=partitionValue combination.
   */
  private List<String> partitions;

  private transient Path locationPath;

  private String location;

  private String uuid;

  public PartitionSpec(List<String> partitions, String location) {
    this.partitions = partitions;
    this.locationPath = new Path(FileFactory.getUpdatedFilePath(location));
    this.location = locationPath.toString();
  }

  public PartitionSpec(List<String> partitions, URI location) {
    this.partitions = partitions;
    this.locationPath = new Path(FileFactory.getUpdatedFilePath(new Path(location).toString()));
    this.location = locationPath.toString();
  }

  public List<String> getPartitions() {
    return partitions;
  }

  public Path getLocation() {
    if (locationPath == null) {
      locationPath = new Path(location);
    }
    return locationPath;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PartitionSpec spec = (PartitionSpec) o;
    return Objects.equals(getLocation(), spec.getLocation());
  }

  @Override public int hashCode() {
    return Objects.hash(locationPath);
  }

  @Override public String toString() {
    return "PartitionSpec{" + "partitions=" + partitions + ", locationPath=" + locationPath
        + ", location='" + location + '\'' + '}';
  }
}
