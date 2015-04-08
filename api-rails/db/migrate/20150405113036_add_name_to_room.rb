class AddNameToRoom < ActiveRecord::Migration
  def change
    add_column :rooms, :name, :string
    add_index :rooms, :name, unique: true
  end
end
