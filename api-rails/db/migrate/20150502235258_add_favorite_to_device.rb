class AddFavoriteToDevice < ActiveRecord::Migration
  def change
    add_column :devices, :favorite, :string
  end
end
