class AddTypeToDevice < ActiveRecord::Migration
  def change
    add_column :devices, :type, :string
  end
end
