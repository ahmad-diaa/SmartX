class AddSecurityQToUsers < ActiveRecord::Migration
  def change
    add_column :users, :securityQ, :string
  end
end
