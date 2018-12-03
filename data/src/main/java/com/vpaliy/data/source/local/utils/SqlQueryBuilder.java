package com.vpaliy.data.source.local.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SqlQueryBuilder {

  private static final String TAG = SqlQueryBuilder.class.getSimpleName();

  private String mTable = null;
  private Map<String, String> mProjectionMap = new HashMap<>();
  private StringBuilder mSelection = new StringBuilder();
  private ArrayList<String> mSelectionArgs = new ArrayList<>();
  private String mGroupBy = null;
  private String mHaving = null;

  /**
   * Reset any internal state, allowing this builder to be recycled.
   */
  public SqlQueryBuilder reset() {
    mTable = null;
    mGroupBy = null;
    mHaving = null;
    mSelection.setLength(0);
    mSelectionArgs.clear();
    return this;
  }

  /**
   * Append the given selection clause to the internal state. Each clause is
   * surrounded with parenthesis and combined using {@code AND}.
   */
  public SqlQueryBuilder where(String selection, String... selectionArgs) {
    if (TextUtils.isEmpty(selection)) {
      if (selectionArgs != null && selectionArgs.length > 0) {
        throw new IllegalArgumentException(
                "Valid selection required when including arguments=");
      }

      // Shortcut when clause is empty
      return this;
    }

    if (mSelection.length() > 0) {
      mSelection.append(" AND ");
    }

    mSelection.append("(").append(selection).append(")");
    if (selectionArgs != null) {
      Collections.addAll(mSelectionArgs, selectionArgs);
    }

    return this;
  }

  public SqlQueryBuilder groupBy(String groupBy) {
    mGroupBy = groupBy;
    return this;
  }

  public SqlQueryBuilder having(String having) {
    mHaving = having;
    return this;
  }

  public SqlQueryBuilder table(String table) {
    mTable = table;
    return this;
  }

  /**
   * Replace positional params in table. Use for JOIN ON conditions.
   */
  public SqlQueryBuilder table(String table, String... tableParams) {
    if (tableParams != null && tableParams.length > 0) {
      String[] parts = table.split("[?]", tableParams.length + 1);
      StringBuilder sb = new StringBuilder(parts[0]);
      for (int i = 1; i < parts.length; i++) {
        sb.append('"').append(tableParams[i - 1]).append('"')
                .append(parts[i]);
      }
      mTable = sb.toString();
    } else {
      mTable = table;
    }
    return this;
  }

  private void assertTable() {
    if (mTable == null) {
      throw new IllegalStateException("Table not specified");
    }
  }

  public SqlQueryBuilder mapToTable(String column, String table) {
    mProjectionMap.put(column, table + "." + column);
    return this;
  }

  public SqlQueryBuilder map(String fromColumn, String toClause) {
    mProjectionMap.put(fromColumn, toClause + " AS " + fromColumn);
    return this;
  }

  /**
   * Return selection string for current internal state.
   *
   * @see #getSelectionArgs()
   */
  public String getSelection() {
    return mSelection.toString();
  }

  /**
   * Return selection arguments for current internal state.
   *
   * @see #getSelection()
   */
  public String[] getSelectionArgs() {
    return mSelectionArgs.toArray(new String[mSelectionArgs.size()]);
  }

  private void mapColumns(String[] columns) {
    for (int i = 0; i < columns.length; i++) {
      final String target = mProjectionMap.get(columns[i]);
      if (target != null) {
        columns[i] = target;
      }
    }
  }

  @Override
  public String toString() {
    return "SelectionBuilder[table=" + mTable + ", selection=" + getSelection()
            + ", selectionArgs=" + Arrays.toString(getSelectionArgs())
            + "projectionMap = " + mProjectionMap + " ]";
  }

  /**
   * Execute query using the current internal state as {@code WHERE} clause.
   */
  public Cursor query(SQLiteDatabase db, String[] columns, String orderBy) {
    return query(db, false, columns, orderBy, null);
  }

  /**
   * Execute query using the current internal state as {@code WHERE} clause.
   */
  public Cursor query(SQLiteDatabase db, boolean distinct, String[] columns, String orderBy,
                      String limit) {
    assertTable();
    if (columns != null) mapColumns(columns);
    Log.d(TAG, "query(columns=" + Arrays.toString(columns)
            + ", distinct=" + distinct + ") " + this);
    return db.query(distinct, mTable, columns, getSelection(), getSelectionArgs(), mGroupBy,
            mHaving, orderBy, limit);
  }

  /**
   * Execute update using the current internal state as {@code WHERE} clause.
   */
  public int update(SQLiteDatabase db, ContentValues values) {
    assertTable();
    Log.d(TAG, "update() " + this);
    return db.update(mTable, values, getSelection(), getSelectionArgs());
  }

  /**
   * Execute delete using the current internal state as {@code WHERE} clause.
   */
  public int delete(SQLiteDatabase db) {
    assertTable();
    Log.d(TAG, "delete() " + this);
    return db.delete(mTable, getSelection(), getSelectionArgs());
  }
}