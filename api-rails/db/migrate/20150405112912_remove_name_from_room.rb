class RemoveNameFromRoom < ActiveRecord::Migration
  def change
    remove_column :rooms, :name, :string
  end
end
